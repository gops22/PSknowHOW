package com.publicissapient.kpidashboard.jira.service;

import com.publicissapient.kpidashboard.common.model.application.AccountHierarchy;
import com.publicissapient.kpidashboard.common.model.application.KanbanAccountHierarchy;
import com.publicissapient.kpidashboard.common.model.jira.*;
import com.publicissapient.kpidashboard.common.repository.application.AccountHierarchyRepository;
import com.publicissapient.kpidashboard.common.repository.application.KanbanAccountHierarchyRepository;
import com.publicissapient.kpidashboard.common.repository.jira.*;
import com.publicissapient.kpidashboard.jira.config.JiraProcessorConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SaveDataImpl implements SaveData {

    @Autowired
    private AccountHierarchyRepository accountHierarchyRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private JiraIssueRepository jiraIssueRepository;

    @Autowired
    private JiraIssueCustomHistoryRepository jiraIssueCustomHistoryRepository;

    @Autowired
    private AssigneeDetailsRepository assigneeDetailsRepository;

    @Autowired
    private KanbanJiraIssueRepository kanbanJiraIssueRepository;

    @Autowired
    private KanbanJiraIssueHistoryRepository kanbanJiraIssueHistoryRepository;

    @Autowired
    private KanbanAccountHierarchyRepository kanbanAccountHierarchyRepository;

    @Override
    public void saveData(List<JiraIssue> jiraIssuesToSave, List<JiraIssueCustomHistory> jiraIssueHistoryToSave, List<SprintDetails> sprintDetailsToSave, Set<AccountHierarchy> accountHierarchiesToSave, AssigneeDetails assigneeDetailsToSave, List<KanbanJiraIssue> kanbanJiraIssues, List<KanbanIssueCustomHistory> kanbanIssueCustomHistoryList, Set<KanbanAccountHierarchy> kanbanAccountHierarchies){

        if(CollectionUtils.isNotEmpty(jiraIssuesToSave) && CollectionUtils.isNotEmpty(jiraIssueHistoryToSave)) {
            log.info("Saving jira issue and jira history");
            jiraIssueRepository.saveAll(jiraIssuesToSave);
            jiraIssueCustomHistoryRepository.saveAll(jiraIssueHistoryToSave);
        }

        if(CollectionUtils.isNotEmpty(sprintDetailsToSave)){
            log.info("Saving Sprint details");
            sprintRepository.saveAll(sprintDetailsToSave);
        }

        if(assigneeDetailsToSave!=null) {
            log.info("Saving assignee details");
            assigneeDetailsRepository.save(assigneeDetailsToSave);
        }

        if (CollectionUtils.isNotEmpty(accountHierarchiesToSave)) {
            log.info("Saving jira account hierarchies");
            accountHierarchyRepository.saveAll(accountHierarchiesToSave);

        }

        if(CollectionUtils.isNotEmpty(kanbanJiraIssues)  && CollectionUtils.isNotEmpty(kanbanIssueCustomHistoryList)){
            log.info("Saving kanban jira issue and kanban jira history");
            kanbanJiraIssueRepository.saveAll(kanbanJiraIssues);
            kanbanJiraIssueHistoryRepository.saveAll(kanbanIssueCustomHistoryList);
        }

        if (CollectionUtils.isNotEmpty(kanbanAccountHierarchies)){
            log.info("Saving account hierarchies kanban");
            kanbanAccountHierarchyRepository.saveAll(kanbanAccountHierarchies);
        }


    }

}
