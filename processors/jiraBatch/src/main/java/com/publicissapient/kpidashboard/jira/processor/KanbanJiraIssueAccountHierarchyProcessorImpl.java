package com.publicissapient.kpidashboard.jira.processor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.publicissapient.kpidashboard.common.constant.CommonConstant;
import com.publicissapient.kpidashboard.common.model.application.AdditionalFilter;
import com.publicissapient.kpidashboard.common.model.application.HierarchyLevel;
import com.publicissapient.kpidashboard.common.model.application.KanbanAccountHierarchy;
import com.publicissapient.kpidashboard.common.model.jira.KanbanJiraIssue;
import com.publicissapient.kpidashboard.common.repository.application.KanbanAccountHierarchyRepository;
import com.publicissapient.kpidashboard.common.service.HierarchyLevelService;
import com.publicissapient.kpidashboard.jira.model.ProjectConfFieldMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KanbanJiraIssueAccountHierarchyProcessorImpl implements KanbanJiraIssueAccountHierarchyProcessor {

	@Autowired
	private KanbanAccountHierarchyRepository kanbanAccountHierarchyRepo;
	@Autowired
	private HierarchyLevelService hierarchyLevelService;
	Map<Pair<String, String>, KanbanAccountHierarchy> existingKanbanHierarchy;
	List<String> additionalFilterCategoryIds;

	@Override
	public Set<KanbanAccountHierarchy> createKanbanAccountHierarchy(KanbanJiraIssue kanbanJiraIssue,
			ProjectConfFieldMapping projectConfig) {

		List<HierarchyLevel> hierarchyLevelList = hierarchyLevelService
				.getFullHierarchyLevels(projectConfig.isKanban());
		Map<String, HierarchyLevel> hierarchyLevelsMap = hierarchyLevelList.stream()
				.collect(Collectors.toMap(HierarchyLevel::getHierarchyLevelId, x -> x));
		HierarchyLevel projectHierarchyLevel = hierarchyLevelsMap.get(CommonConstant.HIERARCHY_LEVEL_ID_PROJECT);

		additionalFilterCategoryIds = hierarchyLevelList.stream()
				.filter(x -> x.getLevel() > projectHierarchyLevel.getLevel()).map(HierarchyLevel::getHierarchyLevelId)
				.collect(Collectors.toList());

		if (null == existingKanbanHierarchy) {
			log.info("Fetching all hierarchy levels");
			List<KanbanAccountHierarchy> accountHierarchyList = kanbanAccountHierarchyRepo.findAll();
			existingKanbanHierarchy = accountHierarchyList.stream()
					.collect(Collectors.toMap(p -> Pair.of(p.getNodeId(), p.getPath()), p -> p));
		}

		Set<KanbanAccountHierarchy> accHierarchyToSave = new HashSet<>();
		if (StringUtils.isNotBlank(kanbanJiraIssue.getProjectName())) {
			KanbanAccountHierarchy projectHierarchy = kanbanAccountHierarchyRepo
					.findByLabelNameAndBasicProjectConfigId(CommonConstant.HIERARCHY_LEVEL_ID_PROJECT,
							new ObjectId(kanbanJiraIssue.getBasicProjectConfigId()))
					.get(0);

			List<KanbanAccountHierarchy> additionalFiltersHierarchies = accountHierarchiesForAdditionalFilters(
					kanbanJiraIssue, projectHierarchy, additionalFilterCategoryIds);

			additionalFiltersHierarchies.forEach(accountHierarchy -> accHierarchyToSave(accountHierarchy,
					existingKanbanHierarchy, accHierarchyToSave));

		}

		return accHierarchyToSave;

	}

	private List<KanbanAccountHierarchy> accountHierarchiesForAdditionalFilters(KanbanJiraIssue jiraIssue,
			KanbanAccountHierarchy projectHierarchy, List<String> additionalFilterCategoryIds) {

		List<KanbanAccountHierarchy> accountHierarchies = new ArrayList<>();
		List<AdditionalFilter> additionalFilters = ListUtils.emptyIfNull(jiraIssue.getAdditionalFilters());

		additionalFilters.forEach(additionalFilter -> {
			if (additionalFilterCategoryIds.contains(additionalFilter.getFilterId())) {
				String labelName = additionalFilter.getFilterId();
				additionalFilter.getFilterValues().forEach(additionalFilterValue -> {
					KanbanAccountHierarchy adFilterAccountHierarchy = new KanbanAccountHierarchy();
					adFilterAccountHierarchy.setLabelName(labelName);
					adFilterAccountHierarchy.setNodeId(additionalFilterValue.getValueId());
					adFilterAccountHierarchy.setNodeName(additionalFilterValue.getValue());
					adFilterAccountHierarchy.setParentId(projectHierarchy.getNodeId());
					adFilterAccountHierarchy.setPath(projectHierarchy.getNodeId()
							+ CommonConstant.ACC_HIERARCHY_PATH_SPLITTER + projectHierarchy.getPath());
					adFilterAccountHierarchy.setBasicProjectConfigId(new ObjectId(jiraIssue.getBasicProjectConfigId()));
					accountHierarchies.add(adFilterAccountHierarchy);
				});
			}

		});

		return accountHierarchies;
	}

	private void accHierarchyToSave(KanbanAccountHierarchy accountHierarchy,
			Map<Pair<String, String>, KanbanAccountHierarchy> existingKanbanHierarchy,
			Set<KanbanAccountHierarchy> accHierarchyToSave) {
		if (StringUtils.isNotBlank(accountHierarchy.getParentId())
				|| (StringUtils.isBlank(accountHierarchy.getParentId()))) {
			KanbanAccountHierarchy exHiery = existingKanbanHierarchy
					.get(Pair.of(accountHierarchy.getNodeId(), accountHierarchy.getPath()));

			if (null == exHiery) {
				accountHierarchy.setCreatedDate(LocalDateTime.now());
				accHierarchyToSave.add(accountHierarchy);
			}
		}
	}

	@Override
	public void cleanAllObjects() {
		additionalFilterCategoryIds = null;
		existingKanbanHierarchy = null;
	}

}
