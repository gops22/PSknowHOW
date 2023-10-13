/*******************************************************************************
 * Copyright 2014 CapitalOne, LLC.
 * Further development Copyright 2022 Sapient Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package com.publicissapient.kpidashboard.jira.listener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publicissapient.kpidashboard.common.constant.ProcessorConstants;
import com.publicissapient.kpidashboard.common.model.ProcessorExecutionTraceLog;
import com.publicissapient.kpidashboard.common.model.jira.JiraIssue;
import com.publicissapient.kpidashboard.common.repository.tracelog.ProcessorExecutionTraceLogRepository;
import com.publicissapient.kpidashboard.common.util.DateUtil;
import com.publicissapient.kpidashboard.jira.constant.JiraConstants;
import com.publicissapient.kpidashboard.jira.model.CompositeResult;

import lombok.extern.slf4j.Slf4j;

/**
 * @author pankumar8
 *
 */
@Component
@Slf4j
public class JiraIssueBoardWriterListener implements ItemWriteListener<CompositeResult> {

	@Autowired
	private ProcessorExecutionTraceLogRepository processorExecutionTraceLogRepo;

	@Override
	public void beforeWrite(List<? extends CompositeResult> compositeResult) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.core.ItemWriteListener#afterWrite(java.util.List)
	 */
	@Override
	public void afterWrite(List<? extends CompositeResult> compositeResults) {

		log.info("Write listner called for scrum board project and saving status in Processor execution Trace log");

		List<ProcessorExecutionTraceLog> processorExecutionToSave = new ArrayList<>();
		List<JiraIssue> jiraIssues = compositeResults.stream().map(CompositeResult::getJiraIssue)
				.collect(Collectors.toList());

		Map<String, Map<String, List<JiraIssue>>> projectBoardWiseIssues = jiraIssues.stream()
				.filter(issue -> !issue.getTypeName().equalsIgnoreCase(JiraConstants.EPIC)).collect(Collectors
						.groupingBy(JiraIssue::getBasicProjectConfigId, Collectors.groupingBy(JiraIssue::getBoardId)));

		for (Map.Entry<String, Map<String, List<JiraIssue>>> entry : projectBoardWiseIssues.entrySet()) {
			String basicProjectConfigId = entry.getKey();
			Map<String, List<JiraIssue>> boardWiseIssues = entry.getValue();
			for (Map.Entry<String, List<JiraIssue>> boardData : boardWiseIssues.entrySet()) {
				String boardId = boardData.getKey();
				JiraIssue firstIssue = boardData
						.getValue().stream().sorted(
								Comparator
										.comparing(
												(JiraIssue jiraIssue) -> LocalDateTime.parse(jiraIssue.getChangeDate(),
														DateTimeFormatter.ofPattern(
																JiraConstants.JIRA_ISSUE_CHANGE_DATE_FORMAT)))
										.reversed())
						.findFirst().orElse(null);
				if (firstIssue != null) {
					Optional<ProcessorExecutionTraceLog> procTraceLog = processorExecutionTraceLogRepo
							.findByProcessorNameAndBasicProjectConfigIdAndBoardId(ProcessorConstants.JIRA,
									basicProjectConfigId, boardId);
					if (procTraceLog.isPresent()) {
						ProcessorExecutionTraceLog processorExecutionTraceLog = procTraceLog.get();
						setTraceLog(processorExecutionTraceLog, basicProjectConfigId, boardId,
								firstIssue.getChangeDate(), processorExecutionToSave);
					} else {
						ProcessorExecutionTraceLog processorExecutionTraceLog = new ProcessorExecutionTraceLog();
						setTraceLog(processorExecutionTraceLog, basicProjectConfigId, boardId,
								firstIssue.getChangeDate(), processorExecutionToSave);
					}
				}
			}

		}
		if (CollectionUtils.isNotEmpty(processorExecutionToSave)) {
			processorExecutionTraceLogRepo.saveAll(processorExecutionToSave);
		}
	}

	private void setTraceLog(ProcessorExecutionTraceLog processorExecutionTraceLog, String basicProjectConfigId,
			String boardId, String changeDate, List<ProcessorExecutionTraceLog> processorExecutionToSave) {
		processorExecutionTraceLog.setBasicProjectConfigId(basicProjectConfigId);
		processorExecutionTraceLog.setBoardId(boardId);
		processorExecutionTraceLog.setExecutionSuccess(true);
		processorExecutionTraceLog.setExecutionEndedAt(System.currentTimeMillis());
		processorExecutionTraceLog.setLastSuccessfulRun(DateUtil.dateTimeConverter(changeDate,
				JiraConstants.JIRA_ISSUE_CHANGE_DATE_FORMAT, DateUtil.DATE_TIME_FORMAT));
		processorExecutionTraceLog.setProcessorName(JiraConstants.JIRA);
		processorExecutionToSave.add(processorExecutionTraceLog);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends CompositeResult> compositeResult) {
		log.error("Exception occured while writing jira Issue ", exception);
	}
}
