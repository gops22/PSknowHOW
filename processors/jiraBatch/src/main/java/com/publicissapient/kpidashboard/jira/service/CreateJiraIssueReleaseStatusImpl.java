package com.publicissapient.kpidashboard.jira.service;

import com.atlassian.jira.rest.client.api.domain.Status;
import com.publicissapient.kpidashboard.common.model.jira.JiraIssueReleaseStatus;
import com.publicissapient.kpidashboard.common.repository.jira.JiraIssueReleaseStatusRepository;
import com.publicissapient.kpidashboard.jira.client.ProcessorJiraRestClient;
import com.publicissapient.kpidashboard.jira.constant.JiraConstants;
import com.publicissapient.kpidashboard.jira.helper.JiraHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CreateJiraIssueReleaseStatusImpl implements CreateJiraIssueReleaseStatus {

	@Autowired
	private JiraIssueReleaseStatusRepository jiraIssueReleaseStatusRepository;

	@Override
	public void processAndSaveProjectStatusCategory(ProcessorJiraRestClient client, String basicProjectConfigId) {

		JiraIssueReleaseStatus jiraIssueReleaseStatus = jiraIssueReleaseStatusRepository
				.findByBasicProjectConfigId(basicProjectConfigId);
		if (null == jiraIssueReleaseStatus) {
			List<Status> listOfProjectStatus = JiraHelper.getStatus(client);
			if (CollectionUtils.isNotEmpty(listOfProjectStatus)) {
				jiraIssueReleaseStatus = new JiraIssueReleaseStatus();
				jiraIssueReleaseStatus.setBasicProjectConfigId(basicProjectConfigId);
				Map<Long, String> toDosList = new HashMap<>();
				Map<Long, String> inProgressList = new HashMap<>();
				Map<Long, String> closedList = new HashMap<>();

				listOfProjectStatus.stream().forEach(status -> {
					if (JiraConstants.TO_DO.equals(status.getStatusCategory().getName())) {
						toDosList.put(status.getId(), status.getName());
					} else if (JiraConstants.DONE.equals(status.getStatusCategory().getName())) {
						closedList.put(status.getId(), status.getName());
					} else {
						inProgressList.put(status.getId(), status.getName());
					}
				});
				jiraIssueReleaseStatus.setToDoList(toDosList);
				jiraIssueReleaseStatus.setInProgressList(inProgressList);
				jiraIssueReleaseStatus.setClosedList(closedList);
				jiraIssueReleaseStatusRepository.save(jiraIssueReleaseStatus);
				log.debug("saved project status category");
			}
		} else {
			log.info("project status category is already in db");
		}
	}

}
