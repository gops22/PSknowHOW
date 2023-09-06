/*******************************************************************************
 * Copyright 2014 CapitalOne, LLC.
 * Further development Copyright 2022 Sapient Corporation.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package com.publicissapient.kpidashboard.apis.jira.scrum.service.release;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publicissapient.kpidashboard.apis.appsetting.service.ConfigHelperService;
import com.publicissapient.kpidashboard.apis.enums.Filters;
import com.publicissapient.kpidashboard.apis.enums.KPICode;
import com.publicissapient.kpidashboard.apis.enums.KPIExcelColumn;
import com.publicissapient.kpidashboard.apis.enums.KPISource;
import com.publicissapient.kpidashboard.apis.errors.ApplicationException;
import com.publicissapient.kpidashboard.apis.jira.service.JiraKPIService;
import com.publicissapient.kpidashboard.apis.model.IterationKpiFilters;
import com.publicissapient.kpidashboard.apis.model.IterationKpiFiltersOptions;
import com.publicissapient.kpidashboard.apis.model.IterationKpiValue;
import com.publicissapient.kpidashboard.apis.model.KPIExcelData;
import com.publicissapient.kpidashboard.apis.model.KpiElement;
import com.publicissapient.kpidashboard.apis.model.KpiRequest;
import com.publicissapient.kpidashboard.apis.model.Node;
import com.publicissapient.kpidashboard.apis.model.TreeAggregatorDetail;
import com.publicissapient.kpidashboard.apis.util.KPIExcelUtility;
import com.publicissapient.kpidashboard.common.model.application.DataCount;
import com.publicissapient.kpidashboard.common.model.application.FieldMapping;
import com.publicissapient.kpidashboard.common.model.jira.JiraIssue;

import lombok.extern.slf4j.Slf4j;

/**
 * This class displays the defects that are part of the release and the phase in
 * which the defect is raised
 *
 * @author eswbogol
 */
@Slf4j
@Component
public class ReleaseDefectByTestPhaseImpl extends JiraKPIService<Integer, List<Object>, Map<String, Object>> {

	private static final String TOTAL_ISSUES = "totalIssues";
	private static final String DEFECTS_COUNT = "Defects Count";
	private static final String OVERALL = "Overall";
	private static final String SEARCH_BY_ASSIGNEE = "Filter by Assignee";
	private static final String SEARCH_BY_PRIORITY = "Filter by Priority";
	private static final String OTHERS = "Others";

	@Autowired
	ConfigHelperService configHelperService;

	/**
	 *
	 * @param kpiRequest
	 *            kpiRequest with request details
	 * @param kpiElement
	 *            basic details of KPI
	 * @param treeAggregatorDetail
	 *            details of project nodes
	 * @return KpiElement with data
	 * @throws ApplicationException
	 *             exception while processing request
	 */
	@Override
	public KpiElement getKpiData(KpiRequest kpiRequest, KpiElement kpiElement,
			TreeAggregatorDetail treeAggregatorDetail) throws ApplicationException {
		treeAggregatorDetail.getMapOfListOfLeafNodes().forEach((k, v) -> {
			if (Filters.getFilter(k) == Filters.RELEASE) {
				releaseWiseLeafNodeValue(v, kpiElement, kpiRequest);
			}
		});
		log.info("ReleaseDefectByTestPhaseImpl -> getKpiData ->  : {}", kpiElement);
		return kpiElement;
	}

	@SuppressWarnings("unchecked")
	private void releaseWiseLeafNodeValue(List<Node> releaseLeafNodeList, KpiElement kpiElement,
			KpiRequest kpiRequest) {
		String requestTrackerId = getRequestTrackerId();
		List<KPIExcelData> excelData = new ArrayList<>();
		List<Node> latestReleaseNode = new ArrayList<>();
		Node latestRelease = releaseLeafNodeList.get(0);

		if (latestRelease != null) {
			Optional.of(latestRelease).ifPresent(latestReleaseNode::add);
			Map<String, Object> resultMap = fetchKPIDataFromDb(latestReleaseNode, null, null, kpiRequest);
			List<JiraIssue> releaseIssues = (List<JiraIssue>) resultMap.get(TOTAL_ISSUES);
			List<IterationKpiValue> filterDataList = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(releaseIssues)) {
				Set<String> assignees = new HashSet<>();
				Set<String> priorities = new HashSet<>();
				createDataCountGroupMap(releaseIssues, assignees, priorities, filterDataList);
				populateExcelDataObject(requestTrackerId, excelData, releaseIssues);
				List<DataCount> dataCountList = new ArrayList<>();
				dataCountList.add(getStatusWiseCountList(releaseIssues));
				constructKPIElementData(kpiElement, excelData, latestRelease, filterDataList, assignees, priorities,
						dataCountList);
			}
			kpiElement.setTrendValueList(filterDataList);
		}
	}

	/**
	 *
	 * @param leafNodeList
	 *            project node details
	 * @param startDate
	 *            startDate
	 * @param endDate
	 *            endDate
	 * @param kpiRequest
	 *            kpiRequest with request details
	 * @return JiraIssues with test phases
	 */
	@Override
	public Map<String, Object> fetchKPIDataFromDb(List<Node> leafNodeList, String startDate, String endDate,
			KpiRequest kpiRequest) {
		Map<String, Object> resultListMap = new HashMap<>();
		Node leafNode = leafNodeList.stream().findFirst().orElse(null);
		if (null != leafNode) {
			log.info("ReleaseDefectByTestPhaseImpl -> Requested sprint : {}", leafNode.getName());
			FieldMapping fieldMapping = configHelperService.getFieldMappingMap()
					.get(leafNode.getProjectFilter().getBasicProjectConfigId());
			List<JiraIssue> releaseIssues = getFilteredReleaseJiraIssuesFromBaseClass(fieldMapping);
			resultListMap.put(TOTAL_ISSUES, releaseIssues);
		}
		return resultListMap;
	}

	public void createDataCountGroupMap(List<JiraIssue> jiraIssueList, Set<String> assigneeNames,
			Set<String> priorities, List<IterationKpiValue> iterationKpiValues) {
		Map<String, Map<String, List<JiraIssue>>> typeAndStatusWiseIssues = getTypeAndStatusWiseIssues(jiraIssueList);
		typeAndStatusWiseIssues.forEach((assigneeName, priorityWiseIssue) -> priorityWiseIssue
				.forEach((priority, issues) -> constructDataCountGroupMap(assigneeNames, priorities, iterationKpiValues,
						assigneeName, priority, issues)));

	}

	private static Map<String, Map<String, List<JiraIssue>>> getTypeAndStatusWiseIssues(List<JiraIssue> jiraIssueList) {
		return jiraIssueList.stream().collect(
				Collectors.groupingBy(jiraIssue -> Optional.ofNullable(jiraIssue.getAssigneeName()).orElse("-"),
						Collectors.groupingBy(JiraIssue::getPriority)));
	}

	private void constructDataCountGroupMap(Set<String> assigneeNames, Set<String> priorities,
			List<IterationKpiValue> iterationKpiValues, String assigneeName, String priority, List<JiraIssue> issues) {
		List<DataCount> dataCountList = new ArrayList<>();
		assigneeNames.add(assigneeName);
		priorities.add(priority);
		dataCountList.add(getStatusWiseCountList(issues));
		IterationKpiValue matchingObject = iterationKpiValues.stream()
				.filter(p -> p.getFilter1().equals(assigneeName) && p.getFilter2().equals(priority)).findAny()
				.orElse(null);
		if (matchingObject == null) {
			IterationKpiValue iterationKpiValue = new IterationKpiValue();
			iterationKpiValue.setFilter1(assigneeName);
			iterationKpiValue.setFilter2(priority);
			iterationKpiValue.setValue(dataCountList);
			iterationKpiValues.add(iterationKpiValue);
		} else {
			matchingObject.getValue().addAll(dataCountList);
		}
	}

	private DataCount getStatusWiseCountList(List<JiraIssue> jiraIssueList) {
		Set<String> testPhasesList = jiraIssueList.stream()
				.filter(jiraIssue -> CollectionUtils.isNotEmpty(jiraIssue.getTestPhaseOfDefectList()))
				.flatMap(jiraIssue -> jiraIssue.getTestPhaseOfDefectList().stream()).collect(Collectors.toSet());
		DataCount dataCount = new DataCount();
		Map<String, Double> releaseProgressCount = new LinkedHashMap<>();
		if (CollectionUtils.isNotEmpty(testPhasesList)) {
			testPhasesList.forEach(s -> getTestPhaseData(jiraIssueList, releaseProgressCount, s));
		} else {
			releaseProgressCount.put(OTHERS, (double) jiraIssueList.stream()
					.filter(jiraIssue -> CollectionUtils.isEmpty(jiraIssue.getTestPhaseOfDefectList())).count());
		}

		dataCount.setValue(releaseProgressCount);
		dataCount
				.setData(String.valueOf(releaseProgressCount.values().stream().mapToDouble(Double::doubleValue).sum()));
		dataCount.setKpiGroup(DEFECTS_COUNT);
		return dataCount;
	}

	private static void getTestPhaseData(List<JiraIssue> jiraIssueList, Map<String, Double> releaseProgressCount,
			String s) {
		releaseProgressCount.put(s,
				(double) jiraIssueList.stream()
						.filter(jiraIssue -> CollectionUtils.isNotEmpty(jiraIssue.getTestPhaseOfDefectList())
								&& jiraIssue.getTestPhaseOfDefectList().contains(s))
						.count());
		releaseProgressCount.put(OTHERS, (double) jiraIssueList.stream()
				.filter(jiraIssue -> CollectionUtils.isEmpty(jiraIssue.getTestPhaseOfDefectList())).count());
	}

	private void populateExcelDataObject(String requestTrackerId, List<KPIExcelData> excelData,
			List<JiraIssue> jiraIssueList) {
		if (requestTrackerId.toLowerCase().contains(KPISource.EXCEL.name().toLowerCase())
				&& CollectionUtils.isNotEmpty(jiraIssueList)) {
			KPIExcelUtility.populateReleaseDefectWithTestPhasesRelatedExcelData(jiraIssueList, excelData);
		}
	}

	private static void constructKPIElementData(KpiElement kpiElement, List<KPIExcelData> excelData, Node latestRelease,
			List<IterationKpiValue> filterDataList, Set<String> assignees, Set<String> priorities,
			List<DataCount> dataCountList) {
		IterationKpiValue overAllIterationKpiValue = new IterationKpiValue();
		overAllIterationKpiValue.setFilter1(OVERALL);
		overAllIterationKpiValue.setFilter2(OVERALL);
		overAllIterationKpiValue.setValue(dataCountList);
		filterDataList.add(overAllIterationKpiValue);
		IterationKpiFiltersOptions filter1 = new IterationKpiFiltersOptions(SEARCH_BY_ASSIGNEE, assignees);
		IterationKpiFiltersOptions filter2 = new IterationKpiFiltersOptions(SEARCH_BY_PRIORITY, priorities);
		IterationKpiFilters iterationKpiFilters = new IterationKpiFilters(filter1, filter2);
		kpiElement.setFilters(iterationKpiFilters);
		kpiElement.setSprint(latestRelease.getName());
		kpiElement.setModalHeads(KPIExcelColumn.RELEASE_DEFECT_BY_TEST_PHASE.getColumns());
		kpiElement.setExcelColumns(KPIExcelColumn.RELEASE_DEFECT_BY_TEST_PHASE.getColumns());
		kpiElement.setExcelData(excelData);
	}

	@Override
	public Integer calculateKPIMetrics(Map<String, Object> stringObjectMap) {
		return null;
	}

	@Override
	public String getQualifierType() {
		return KPICode.RELEASE_DEFECT_BY_TEST_PHASE.name();
	}
}
