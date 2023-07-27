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
package com.publicissapient.kpidashboard.apis.jira.scrum.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publicissapient.kpidashboard.apis.appsetting.service.ConfigHelperService;
import com.publicissapient.kpidashboard.apis.enums.Filters;
import com.publicissapient.kpidashboard.apis.enums.KPICode;
import com.publicissapient.kpidashboard.apis.enums.KPIExcelColumn;
import com.publicissapient.kpidashboard.apis.enums.KPISource;
import com.publicissapient.kpidashboard.apis.errors.ApplicationException;
import com.publicissapient.kpidashboard.apis.jira.service.JiraKPIService;
import com.publicissapient.kpidashboard.apis.model.IterationKpiValue;
import com.publicissapient.kpidashboard.apis.model.KPIExcelData;
import com.publicissapient.kpidashboard.apis.model.KpiElement;
import com.publicissapient.kpidashboard.apis.model.KpiRequest;
import com.publicissapient.kpidashboard.apis.model.Node;
import com.publicissapient.kpidashboard.apis.model.TreeAggregatorDetail;
import com.publicissapient.kpidashboard.apis.util.KPIExcelUtility;
import com.publicissapient.kpidashboard.common.constant.CommonConstant;
import com.publicissapient.kpidashboard.common.model.application.DataCount;
import com.publicissapient.kpidashboard.common.model.application.FieldMapping;
import com.publicissapient.kpidashboard.common.model.jira.JiraIssue;
import com.publicissapient.kpidashboard.common.repository.jira.JiraIssueRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BackLogCountByStatusServiceImpl extends JiraKPIService<Integer, List<Object>, Map<String, Object>> {

	@Autowired
	private JiraIssueRepository jiraIssueRepository;
	@Autowired
	private ConfigHelperService configHelperService;

	private static final String PROJECT_WISE_JIRA_ISSUE = "Jira Issue";

	@Override
	public Integer calculateKPIMetrics(Map<String, Object> stringObjectMap) {
		return null;
	}

	@Override
	public Map<String, Object> fetchKPIDataFromDb(List<Node> leafNodeList, String startDate, String endDate,
			KpiRequest kpiRequest) {

		Map<String, Object> resultListMap = new HashMap<>();
		Node leafNode = leafNodeList.stream().findFirst().orElse(null);

		if (leafNode != null) {
			log.info("BackLog Count By Status kpi -> Requested project : {}", leafNode.getProjectFilter().getName());
			List<JiraIssue> totalJiraIssue = getFilteredReleaseJiraIssuesFromBaseClass(new HashMap<>());
			resultListMap.put(PROJECT_WISE_JIRA_ISSUE, totalJiraIssue);
		}

		return resultListMap;
	}

	@Override
	public String getQualifierType() {
		return KPICode.BACKLOG_ISSUE_COUNT_BY_STATUS.name();
	}

	@Override
	public KpiElement getKpiData(KpiRequest kpiRequest, KpiElement kpiElement,
			TreeAggregatorDetail treeAggregatorDetail) throws ApplicationException {

		treeAggregatorDetail.getMapOfListOfProjectNodes().forEach((k, v) -> {
			Filters filters = Filters.getFilter(k);
			if (Filters.PROJECT == filters) {
				projectWiseLeafNodeValue(v, kpiElement, kpiRequest);
			}
		});
		log.info("BackLogCountByStatusServiceImpl -> getKpiData ->  : {}", kpiElement);
		return kpiElement;
	}

	private static void getIssuesStatusCount(Map<String, List<JiraIssue>> statusData,
			Map<String, Integer> statusWiseCountMap) {
		for (Map.Entry<String, List<JiraIssue>> statusEntry : statusData.entrySet()) {
			statusWiseCountMap.put(statusEntry.getKey(), statusEntry.getValue().size());
		}
	}

	/**
	 *
	 * @param leafNodeList
	 * @param kpiElement
	 * @param kpiRequest
	 */
	private void projectWiseLeafNodeValue(List<Node> leafNodeList, KpiElement kpiElement, KpiRequest kpiRequest) {

		String requestTrackerId = getRequestTrackerId();

		List<KPIExcelData> excelData = new ArrayList<>();

		Node leafNode = leafNodeList.stream().findFirst().orElse(null);

		if (leafNode != null) {
			FieldMapping fieldMapping = configHelperService.getFieldMappingMap()
					.get(leafNode.getProjectFilter().getBasicProjectConfigId());

			Map<String, Object> resultMap = fetchKPIDataFromDb(leafNodeList, "", "", kpiRequest);

			List<JiraIssue> jiraIssues = (List<JiraIssue>) resultMap.get(PROJECT_WISE_JIRA_ISSUE);
			List<IterationKpiValue> filterDataList = new ArrayList<>();
			Set<String> excludeStatuses = new HashSet<>();

			excludeStatuses.add(Optional.ofNullable(fieldMapping.getJiraDefectRejectionStatusKPI151()).orElse(""));

			if (Optional.ofNullable(fieldMapping.getJiraDodKPI151()).isPresent()) {
				excludeStatuses.addAll(fieldMapping.getJiraDodKPI151());
			}

			if (Optional.ofNullable(fieldMapping.getJiraLiveStatusKPI151()).isPresent()) {
				excludeStatuses.add(fieldMapping.getJiraLiveStatusKPI151());
			}

			// exclude the issue from total jiraIssues based on DOD status and Defect
			// Rejection Status
			if (CollectionUtils.isNotEmpty(excludeStatuses)) {
				Set<String> excludeStatus = excludeStatuses.stream().map(String::toUpperCase)
						.collect(Collectors.toSet());
				jiraIssues = jiraIssues.stream()
						.filter(jiraIssue -> !excludeStatus.contains(jiraIssue.getJiraStatus().toUpperCase()))
						.collect(Collectors.toList());
			}

			if (CollectionUtils.isNotEmpty(jiraIssues)) {

				log.info("Backlog Count By Status -> request id : {} total jira Issues : {}", requestTrackerId,
						jiraIssues.size());

				Map<String, List<JiraIssue>> statusWiseIssuesList = jiraIssues.stream()
						.collect(Collectors.groupingBy(JiraIssue::getStatus));

				log.info("statusWiseIssuesList ->  : {}", statusWiseIssuesList);
				Map<String, Integer> statusWiseCountMap = new HashMap<>();
				getIssuesStatusCount(statusWiseIssuesList, statusWiseCountMap);
				if (MapUtils.isNotEmpty(statusWiseCountMap)) {
					List<DataCount> trendValueListOverAll = new ArrayList<>();
					DataCount overallData = new DataCount();
					int sumOfDefectsCount = statusWiseCountMap.values().stream().mapToInt(Integer::intValue).sum();
					overallData.setData(String.valueOf(sumOfDefectsCount));
					overallData.setValue(statusWiseCountMap);
					overallData.setKpiGroup(CommonConstant.OVERALL);
					overallData.setSProjectName(leafNode.getProjectFilter().getName());
					trendValueListOverAll.add(overallData);

					List<DataCount> middleTrendValueListOverAll = new ArrayList<>();
					DataCount middleOverallData = new DataCount();
					middleOverallData.setData(leafNode.getProjectFilter().getName());
					middleOverallData.setValue(trendValueListOverAll);
					middleTrendValueListOverAll.add(middleOverallData);
					populateExcelDataObject(requestTrackerId, excelData, jiraIssues);
					IterationKpiValue filterDataOverall = new IterationKpiValue(CommonConstant.OVERALL,
							middleTrendValueListOverAll);
					filterDataList.add(filterDataOverall);
					kpiElement.setModalHeads(KPIExcelColumn.BACKLOG_COUNT_BY_STATUS.getColumns());
					kpiElement.setExcelColumns(KPIExcelColumn.BACKLOG_COUNT_BY_STATUS.getColumns());
					kpiElement.setExcelData(excelData);
					log.info("BacklogCountByStatusServiceImpl -> request id : {} total jira Issues : {}",
							requestTrackerId, filterDataList.get(0));
				}

			}
			kpiElement.setTrendValueList(filterDataList);
		}
	}

	private void populateExcelDataObject(String requestTrackerId, List<KPIExcelData> excelData,
			List<JiraIssue> jiraIssueList) {
		if (requestTrackerId.toLowerCase().contains(KPISource.EXCEL.name().toLowerCase())
				&& CollectionUtils.isNotEmpty(jiraIssueList)) {
			KPIExcelUtility.populateBacklogCountExcelData(jiraIssueList, excelData);
		}
	}
}
