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

package com.publicissapient.kpidashboard.apis.jira.scrum.service.release;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publicissapient.kpidashboard.apis.appsetting.service.ConfigHelperService;
import com.publicissapient.kpidashboard.apis.common.service.impl.CommonServiceImpl;
import com.publicissapient.kpidashboard.apis.constant.Constant;
import com.publicissapient.kpidashboard.apis.enums.Filters;
import com.publicissapient.kpidashboard.apis.enums.KPICode;
import com.publicissapient.kpidashboard.apis.enums.KPIExcelColumn;
import com.publicissapient.kpidashboard.apis.enums.KPISource;
import com.publicissapient.kpidashboard.apis.errors.ApplicationException;
import com.publicissapient.kpidashboard.apis.jira.service.JiraKPIService;
import com.publicissapient.kpidashboard.apis.model.CustomDateRange;
import com.publicissapient.kpidashboard.apis.model.IterationKpiValue;
import com.publicissapient.kpidashboard.apis.model.KPIExcelData;
import com.publicissapient.kpidashboard.apis.model.KpiElement;
import com.publicissapient.kpidashboard.apis.model.KpiRequest;
import com.publicissapient.kpidashboard.apis.model.Node;
import com.publicissapient.kpidashboard.apis.model.TreeAggregatorDetail;
import com.publicissapient.kpidashboard.apis.util.KPIExcelUtility;
import com.publicissapient.kpidashboard.apis.util.KpiDataHelper;
import com.publicissapient.kpidashboard.common.constant.CommonConstant;
import com.publicissapient.kpidashboard.common.model.application.DataCount;
import com.publicissapient.kpidashboard.common.model.application.DataCountGroup;
import com.publicissapient.kpidashboard.common.model.application.FieldMapping;
import com.publicissapient.kpidashboard.common.model.jira.JiraHistoryChangeLog;
import com.publicissapient.kpidashboard.common.model.jira.JiraIssue;
import com.publicissapient.kpidashboard.common.model.jira.JiraIssueCustomHistory;
import com.publicissapient.kpidashboard.common.model.jira.JiraIssueReleaseStatus;
import com.publicissapient.kpidashboard.common.repository.jira.JiraIssueRepository;
import com.publicissapient.kpidashboard.common.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * This service for managing Release BurnUp Kpi on Release Board. Gives analysis
 * of release scope vs progress for released version & release prediction based
 * on closure rate for unreleased version. {@link JiraKPIService}
 */
@Slf4j
@Component
public class ReleaseBurnupServiceImpl extends JiraKPIService<Integer, List<Object>, Map<String, Object>> {

	private static final String TOTAL_ISSUES = "totalIssues";
	private static final String ADDED_TO_RELEASE = "addedToRelease";
	private static final String FULL_RELEASE = "fullRelease";
	private static final String REMOVED_FROM_RELEASE = "removedFromRelease";
	private static final String ISSUE_COUNT = "Issue Count";
	private static final String STORY_POINT = "Story Point";
	private static final String SCOPE_REMOVED = "Scope Removed";
	private static final String SCOPE_ADDED = "Scope Added";
	private static final String RELEASE_SCOPE = "Release Scope";
	private static final int DAYS_RANGE = 120;
	private static final String RELEASE_PROGRESS = "Release Progress";
	private static final String LINE_GRAPH_TYPE = "line";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final String RELEASE_PREDICTION = "Release Prediction";
	public static final String ISSUE_COUNT_PREDICTION = "issueCountPrediction";
	public static final String ISSUE_SIZE_PREDICTION = "issueSizePrediction";
	public static final String SHOW_PREDICTION = "showPrediction";
	public static final String OVERALL_COMPLETED = "OVERALL COMPLETED";
	private static final String AVG_ISSUE_COUNT = "avgIssueCount";
	private static final String AVG_STORY_POINT = "avgStoryPoint";
	public static final String IS_PREDICTION_BOUNDARY = "isPredictionBoundary";
	@Autowired
	private JiraIssueRepository jiraIssueRepository;

	@Autowired
	private ConfigHelperService configHelperService;

	@Autowired
	private CommonServiceImpl commonService;

	private LocalDate tempStartDate = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KpiElement getKpiData(KpiRequest kpiRequest, KpiElement kpiElement,
			TreeAggregatorDetail treeAggregatorDetail) throws ApplicationException {
		treeAggregatorDetail.getMapOfListOfLeafNodes().forEach((k, v) -> {
			if (Filters.getFilter(k) == Filters.RELEASE) {
				releaseWiseLeafNodeValue(v, kpiElement, kpiRequest);
			}
		});
		log.info("ReleaseBurnUpServiceImpl -> getKpiData ->  : {}", kpiElement);
		return kpiElement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> fetchKPIDataFromDb(List<Node> leafNodeList, String startDate, String endDate,
			KpiRequest kpiRequest) {
		Map<String, Object> resultListMap = new HashMap<>();
		Node leafNode = leafNodeList.stream().findFirst().orElse(null);
		if (null != leafNode) {
			log.info("Release Progress -> Requested sprint : {}", leafNode.getName());

			List<String> releaseList = getReleaseList();
			if (CollectionUtils.isNotEmpty(releaseList)) {
				List<JiraIssueCustomHistory> allIssuesHistory = getJiraIssuesCustomHistoryFromBaseClass();
				List<JiraIssue> releaseIssues = jiraIssueRepository.findByNumberInAndBasicProjectConfigId(
						allIssuesHistory.stream().map(JiraIssueCustomHistory::getStoryID).collect(Collectors.toList()),
						leafNode.getProjectFilter().getBasicProjectConfigId().toString());

				Map<LocalDate, List<JiraIssue>> addedIssuesMap = new HashMap<>();
				Map<LocalDate, List<JiraIssue>> removeIssueMap = new HashMap<>();
				Map<LocalDate, List<JiraIssue>> fullReleaseMap = new HashMap<>();
				Map<LocalDate, List<JiraIssue>> completedReleaseMap = new HashMap<>();
				dateWiseLogs(allIssuesHistory, releaseList.stream().findFirst().orElse(null), releaseIssues,
						addedIssuesMap, removeIssueMap, fullReleaseMap, completedReleaseMap);
				resultListMap.put(FULL_RELEASE, fullReleaseMap);
				resultListMap.put(ADDED_TO_RELEASE, addedIssuesMap);
				resultListMap.put(REMOVED_FROM_RELEASE, removeIssueMap);
				resultListMap.put(TOTAL_ISSUES, releaseIssues);
				resultListMap.put(RELEASE_PROGRESS, completedReleaseMap);
			}

		}
		return resultListMap;
	}

	/**
	 * Used to Create Date wise log
	 * 
	 * @param allIssuesHistory
	 *            List<JiraIssueCustomHistory>
	 * @param releaseName
	 *            Name of release
	 * @param releaseIssue
	 *            List<JiraIssue>
	 * @param addedIssuesMap
	 *            Map<LocalDate, List<JiraIssue>>
	 * @param removeIssueMap
	 *            Map<LocalDate, List<JiraIssue>>
	 * @param fullReleaseMap
	 *            Map<LocalDate, List<JiraIssue>>
	 * @param completedReleaseMap
	 *            Map<LocalDate, List<JiraIssue>>
	 */
	private void dateWiseLogs(List<JiraIssueCustomHistory> allIssuesHistory, String releaseName,
			List<JiraIssue> releaseIssue, Map<LocalDate, List<JiraIssue>> addedIssuesMap,
			Map<LocalDate, List<JiraIssue>> removeIssueMap, Map<LocalDate, List<JiraIssue>> fullReleaseMap,
			Map<LocalDate, List<JiraIssue>> completedReleaseMap) {

		releaseName = releaseName != null ? releaseName : "";
		String finalReleaseName = releaseName.toLowerCase();
		allIssuesHistory.forEach(issueHistory -> {
			List<JiraHistoryChangeLog> fixVersionUpdationLog = issueHistory.getFixVersionUpdationLog();
			Collections.sort(fixVersionUpdationLog, Comparator.comparing(JiraHistoryChangeLog::getUpdatedOn));
			int lastIndex = fixVersionUpdationLog.size() - 1;
			fixVersionUpdationLog.stream()
					.filter(updateLogs -> updateLogs.getChangedTo().toLowerCase().contains(finalReleaseName)
							|| updateLogs.getChangedFrom().toLowerCase().contains(finalReleaseName))
					.forEach(updateLogs -> {
						List<JiraIssue> jiraIssueList = getRespectiveJiraIssue(releaseIssue, issueHistory);
						LocalDate updatedLog = updateLogs.getUpdatedOn().toLocalDate();
						if (updateLogs.getChangedTo().toLowerCase().contains(finalReleaseName)) {
							if (fixVersionUpdationLog.get(lastIndex).getChangedTo().toLowerCase()
									.contains(finalReleaseName)) {
								List<JiraIssue> cloneList = new ArrayList<>(jiraIssueList);
								fullReleaseMap.computeIfPresent(updatedLog, (k, v) -> {
									v.addAll(cloneList);
									return v;
								});
								fullReleaseMap.putIfAbsent(updatedLog, cloneList);
							}

							addedIssuesMap.computeIfPresent(updatedLog, (k, v) -> {
								v.addAll(jiraIssueList);
								return v;
							});
							addedIssuesMap.putIfAbsent(updatedLog, jiraIssueList);
						}
						if (updateLogs.getChangedFrom().toLowerCase().contains(finalReleaseName)) {
							List<JiraIssue> removeJiraIssueLIst = new ArrayList<>(jiraIssueList);
							updatedLog = updateLogs.getUpdatedOn().toLocalDate();
							removeIssueMap.computeIfPresent(updatedLog, (k, v) -> {
								v.addAll(removeJiraIssueLIst);
								return v;
							});
							removeIssueMap.putIfAbsent(updatedLog, removeJiraIssueLIst);
						}

					});
			createCompletedIssuesDateWiseMap(issueHistory, completedReleaseMap, releaseIssue);

		});
	}

	/**
	 * Create Completed Issue Date Wise
	 * 
	 * @param issueHistory
	 *            Issue History
	 * @param completedIssues
	 *            Map<LocalDate, List<JiraIssue>>
	 * @param totalIssueList
	 *            List<JiraIssue>
	 */
	private void createCompletedIssuesDateWiseMap(JiraIssueCustomHistory issueHistory,
			Map<LocalDate, List<JiraIssue>> completedIssues, List<JiraIssue> totalIssueList) {
		List<JiraHistoryChangeLog> statusUpdationLog = issueHistory.getStatusUpdationLog();
		JiraIssueReleaseStatus jiraIssueReleaseStatus = getJiraIssueReleaseStatus();
		statusUpdationLog = statusUpdationLog.stream()
				.filter(log -> jiraIssueReleaseStatus.getClosedList().containsValue(log.getChangedTo())
						|| jiraIssueReleaseStatus.getClosedList().containsValue(log.getChangedFrom()))
				.collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(statusUpdationLog)) {
			Map<String, LocalDate> closedStatusDateMap = new HashMap<>();
			for (JiraHistoryChangeLog jiraHistoryChangeLog : statusUpdationLog) {
				LocalDate activityDate = LocalDate.parse(jiraHistoryChangeLog.getUpdatedOn().toString().split("T")[0],
						DATE_TIME_FORMATTER);
				if (jiraIssueReleaseStatus.getClosedList().containsValue(jiraHistoryChangeLog.getChangedTo())) {
					if (closedStatusDateMap.containsKey(jiraHistoryChangeLog.getChangedTo())) {
						closedStatusDateMap.clear();
					}
					closedStatusDateMap.put(jiraHistoryChangeLog.getChangedTo(), activityDate);
				}
			}
			// Getting the min date of closed status.
			LocalDate updatedLog = closedStatusDateMap.values().stream().filter(Objects::nonNull)
					.min(LocalDate::compareTo).orElse(null);
			List<JiraIssue> jiraIssueList = new ArrayList<>(getRespectiveJiraIssue(totalIssueList, issueHistory));
			LocalDate finalUpdatedLog = updatedLog;
			jiraIssueList.forEach(issue -> issue.setUpdateDate(ObjectUtils.isEmpty(finalUpdatedLog)
					? LocalDate.parse(issue.getUpdateDate().split("T")[0], DATE_TIME_FORMATTER).toString()
					: finalUpdatedLog.toString()));
			completedIssues.computeIfPresent(updatedLog, (k, v) -> {
				v.addAll(jiraIssueList);
				return v;
			});
			completedIssues.putIfAbsent(updatedLog, jiraIssueList);
		}
	}

	/**
	 * Populate Release Wise Leaf Node Value
	 * 
	 * @param releaseLeafNodeList
	 *            List<Node>
	 * @param kpiElement
	 *            kpiElement
	 * @param kpiRequest
	 *            kpiRequest
	 */
	@SuppressWarnings("unchecked")
	private void releaseWiseLeafNodeValue(List<Node> releaseLeafNodeList, KpiElement kpiElement, // NOSONAR
			KpiRequest kpiRequest) {
		String requestTrackerId = getRequestTrackerId();
		List<KPIExcelData> excelData = new ArrayList<>();
		List<Node> latestReleaseNode = new ArrayList<>();
		Node latestRelease = releaseLeafNodeList.get(0);

		if (latestRelease == null) {
			return;
		}
		String startDate = latestRelease.getReleaseFilter().getStartDate();
		String endDate = latestRelease.getReleaseFilter().getEndDate();
		String releaseState = Optional.ofNullable(latestRelease.getAccountHierarchy().getReleaseState()).orElse("");
		Optional.ofNullable(latestRelease).ifPresent(latestReleaseNode::add);

		Map<String, Object> resultMap = fetchKPIDataFromDb(latestReleaseNode, null, null, kpiRequest);
		List<JiraIssue> releaseIssues = (List<JiraIssue>) resultMap.get(TOTAL_ISSUES);
		Map<LocalDate, List<JiraIssue>> completedReleaseMap = (Map<LocalDate, List<JiraIssue>>) resultMap
				.get(RELEASE_PROGRESS);
		Map<LocalDate, List<JiraIssue>> addedIssuesMap = (Map<LocalDate, List<JiraIssue>>) resultMap
				.get(ADDED_TO_RELEASE);
		Map<LocalDate, List<JiraIssue>> fullReleaseIssueMap = (Map<LocalDate, List<JiraIssue>>) resultMap
				.get(FULL_RELEASE);

		Map<LocalDate, List<JiraIssue>> removeIssueMap = (Map<LocalDate, List<JiraIssue>>) resultMap
				.get(REMOVED_FROM_RELEASE);

		List<IterationKpiValue> iterationKpiValueList = new ArrayList<>();
		long range = 0;
		String duration;
		if (CollectionUtils.isNotEmpty(releaseIssues) && MapUtils.isNotEmpty(fullReleaseIssueMap)) {
			Object basicProjectConfigId = latestRelease.getProjectFilter().getBasicProjectConfigId();
			FieldMapping fieldMapping = configHelperService.getFieldMappingMap().get(basicProjectConfigId);
			/*
			 * if starttime is absent, then the date at which issue was added and remained
			 * added in the entire relase is considered to be the start date if end date is
			 * absent then it means that issue is unreleased, so till today we can consider
			 * as end date
			 */
			LocalDate startLocalDate = StringUtils.isEmpty(startDate) ? fullReleaseIssueMap.keySet().stream()
					.filter(Objects::nonNull).min(LocalDate::compareTo).orElse(null)
					: LocalDate.parse(startDate.split("T")[0], DATE_TIME_FORMATTER);
			LocalDate endLocalDate = StringUtils.isEmpty(endDate) ? LocalDate.now()
					: LocalDate.parse(endDate.split("T")[0], DATE_TIME_FORMATTER);

			Map<String, Long> durationRangeMap = getDurationRangeMap(startLocalDate, endLocalDate);
			duration = durationRangeMap.keySet().stream().findFirst().orElse("");
			range = durationRangeMap.values().stream().findFirst().orElse(0L);
			completedReleaseMap = prepareCompletedIssueMap(completedReleaseMap, startLocalDate);

			tempStartDate = LocalDate.parse(startLocalDate.toString());
			List<JiraIssue> overallIssues = new ArrayList<>();
			List<JiraIssue> overallCompletedIssues = new ArrayList<>();
			List<DataCountGroup> issueCountDataGroup = new ArrayList<>();
			List<DataCountGroup> issueSizeCountDataGroup = new ArrayList<>();
			Map<String, Object> predictionDataMap = new HashMap<>();
			if (releaseState.equalsIgnoreCase(CommonConstant.RELEASED) || MapUtils.isEmpty(completedReleaseMap)) {
				// populating only release scope vs progress
				for (int i = 0; i < range && !startLocalDate.isAfter(endLocalDate); i++) {
					DataCountGroup issueCount = new DataCountGroup();
					DataCountGroup issueSize = new DataCountGroup();
					CustomDateRange dateRange = KpiDataHelper.getStartAndEndDateForDataFiltering(startLocalDate,
							duration);
					Map<String, List<JiraIssue>> filterWiseGroupedMap = createFilterWiseGroupedMap(dateRange,
							addedIssuesMap, removeIssueMap, fullReleaseIssueMap, overallIssues, completedReleaseMap,
							overallCompletedIssues);
					overallCompletedIssues = filterWiseGroupedMap.getOrDefault(OVERALL_COMPLETED, new ArrayList<>());
					String date = getRange(dateRange, duration);
					populateFilterWiseDataMap(filterWiseGroupedMap, issueCount, issueSize, date, duration, fieldMapping,
							predictionDataMap);
					startLocalDate = getNextRangeDate(duration, startLocalDate);
					issueCountDataGroup.add(issueCount);
					issueSizeCountDataGroup.add(issueSize);
				}
			} else {
				// populating release scope vs release progress followed by its prediction on
				// avg completion
				double avgIssueCount = 0L;
				double avgStoryPoint = 0d;
				Map<String, Object> averageDataMap = getAverageData(fieldMapping, startLocalDate, completedReleaseMap);
				avgIssueCount = (double) averageDataMap.getOrDefault(AVG_ISSUE_COUNT, 0L);
				avgStoryPoint = (double) averageDataMap.getOrDefault(AVG_STORY_POINT, 0d);

				// finding the release scope and progress for which prediction need to be cal
				CustomDateRange dateRangeTillToday = new CustomDateRange();
				dateRangeTillToday.setStartDate(startLocalDate);
				dateRangeTillToday.setEndDate(LocalDate.now());

				List<JiraIssue> overallIssue = new ArrayList<>();
				List<JiraIssue> overallCompletedIssue = new ArrayList<>();
				Map<String, List<JiraIssue>> filterWiseGroupedMapTillNow = createFilterWiseGroupedMap(
						dateRangeTillToday, addedIssuesMap, removeIssueMap, fullReleaseIssueMap, overallIssue,
						completedReleaseMap, overallCompletedIssue);
				List<JiraIssue> releaseScopeToReach = filterWiseGroupedMapTillNow.getOrDefault(RELEASE_SCOPE,
						new ArrayList<>());
				List<JiraIssue> releaseProgressTillNow = filterWiseGroupedMapTillNow.getOrDefault(RELEASE_PROGRESS,
						new ArrayList<>());

				// based on the value to reach & avg value finding the Prediction date
				final LocalDate predictionEndDate = calPredictionEndDate(releaseScopeToReach, releaseProgressTillNow,
						fieldMapping, avgIssueCount, avgStoryPoint);

				// using the prediction end date & release start date to cal x-axis duration &
				// range
				Map<String, Long> durationMapPrediction = getDurationRangeMap(startLocalDate, predictionEndDate);
				duration = durationMapPrediction.keySet().stream().findFirst().orElse("");
				range = durationMapPrediction.values().stream().findFirst().orElse(0L);

				// setting the initial value form which prediction population will start
				double issueCountPrediction = releaseProgressTillNow.size();
				double issueSizePrediction = roundingOff(getStoryPoint(releaseProgressTillNow, fieldMapping));
				predictionDataMap.put(ISSUE_COUNT_PREDICTION, issueCountPrediction);
				predictionDataMap.put(ISSUE_SIZE_PREDICTION, issueSizePrediction);
				boolean isPredictionBoundary = true; // used to check and add data point of release
													 // scope,progress,prediction
				for (int i = 0; i < range && !startLocalDate.isAfter(predictionEndDate); i++) {
					DataCountGroup issueCount = new DataCountGroup();
					DataCountGroup issueSize = new DataCountGroup();
					CustomDateRange dateRange = KpiDataHelper.getStartAndEndDateForDataFiltering(startLocalDate,
							duration);
					Map<String, List<JiraIssue>> filterWiseGroupedMap = createFilterWiseGroupedMap(dateRange,
							addedIssuesMap, removeIssueMap, fullReleaseIssueMap, overallIssues, completedReleaseMap,
							overallCompletedIssues);
					overallCompletedIssues = filterWiseGroupedMap.getOrDefault(OVERALL_COMPLETED, new ArrayList<>());
					String date = getRange(dateRange, duration);
					// start populate the release prediction if startDate is greater than today
					isPredictionBoundary = getNextPredictionDataPt(startLocalDate, predictionDataMap, dateRange,
							isPredictionBoundary, releaseScopeToReach, averageDataMap, fieldMapping);
					populateFilterWiseDataMap(filterWiseGroupedMap, issueCount, issueSize, date, duration, fieldMapping,
							predictionDataMap);
					startLocalDate = getNextRangeDate(duration, startLocalDate);
					issueCountDataGroup.add(issueCount);
					issueSizeCountDataGroup.add(issueSize);
				}
			}
			populateExcelDataObject(requestTrackerId, excelData, releaseIssues, fieldMapping);
			createExcelDataAndTrendValueList(kpiElement, excelData, iterationKpiValueList, issueCountDataGroup,
					issueSizeCountDataGroup);

		}
		kpiElement.setTrendValueList(iterationKpiValueList);
	}

	/**
	 * Method used to get successive data point of prediction
	 * 
	 * @param startLocalDate
	 *            Population Date
	 * @param predictionDataMap
	 *            Map<String, Object>
	 * @param dateRange
	 *            Date Range for start & end Date
	 * @param isPredictionBoundary
	 *            Flag of start of prediction
	 * @param releaseScopeToReach
	 *            List<JiraIssue>
	 * @param averageDataMap
	 *            Map<String, Object>
	 * @param fieldMapping
	 *            fieldMapping
	 * @return prediction boundary flag
	 */
	private boolean getNextPredictionDataPt(LocalDate startLocalDate, Map<String, Object> predictionDataMap,
			CustomDateRange dateRange, boolean isPredictionBoundary, List<JiraIssue> releaseScopeToReach,
			Map<String, Object> averageDataMap, FieldMapping fieldMapping) {
		double issueSizePrediction;
		double issueCountPrediction;
		double avgIssueCount = (double) averageDataMap.getOrDefault(AVG_ISSUE_COUNT, 0L);
		double avgStoryPoint = (double) averageDataMap.getOrDefault(AVG_STORY_POINT, 0d);

		if (!startLocalDate.isBefore(LocalDate.now())) {
			issueCountPrediction = (double) predictionDataMap.get(ISSUE_COUNT_PREDICTION);
			issueSizePrediction = (double) predictionDataMap.get(ISSUE_SIZE_PREDICTION);
			long daysInterval = ChronoUnit.DAYS.between(dateRange.getStartDate(), dateRange.getEndDate()) + 1;
			if (!isPredictionBoundary) {
				// cal the next issueCount/Sp for prediction data
				issueCountPrediction = roundingOff(
						Math.min((issueCountPrediction + avgIssueCount) * daysInterval, releaseScopeToReach.size()));
				issueSizePrediction = roundingOff(Math.min((issueSizePrediction + avgStoryPoint) * daysInterval,
						getStoryPoint(releaseScopeToReach, fieldMapping)));
			}
			predictionDataMap.put(ISSUE_COUNT_PREDICTION, issueCountPrediction);
			predictionDataMap.put(ISSUE_SIZE_PREDICTION, issueSizePrediction);
			predictionDataMap.put(SHOW_PREDICTION, true);
			predictionDataMap.put(IS_PREDICTION_BOUNDARY, isPredictionBoundary);
			isPredictionBoundary = false;
		}
		return isPredictionBoundary;
	}

	/**
	 * Based on Average Closure Rate & Release Scope to Reach cal prediction End
	 * Date
	 * 
	 * @param releaseScopeToReach
	 *            List<JiraIssue>
	 * @param releaseProgressTillNow
	 *            List<JiraIssue>
	 * @param fieldMapping
	 *            fieldMapping
	 * @param avgIssueCount
	 *            avg Issue Count
	 * @param avgStoryPoint
	 *            avg SP
	 * @return Prediction End Date
	 */
	private LocalDate calPredictionEndDate(List<JiraIssue> releaseScopeToReach, List<JiraIssue> releaseProgressTillNow,
			FieldMapping fieldMapping, double avgIssueCount, double avgStoryPoint) {
		// finding the prediction end date
		long timeRequiredForIssueCount = 0;
		long timeRequiredForSp = 0;
		int remainingIssues = releaseScopeToReach.size() - releaseProgressTillNow.size();
		double remainingSp = getStoryPoint(releaseScopeToReach, fieldMapping)
				- getStoryPoint(releaseProgressTillNow, fieldMapping);
		if (avgIssueCount != 0) {
			timeRequiredForIssueCount = (long) (remainingIssues / avgIssueCount);
		}
		if (avgStoryPoint != 0) {
			timeRequiredForSp = (long) (remainingSp / avgStoryPoint);
		}
		return LocalDate.now().plusDays(Math.max(timeRequiredForSp, timeRequiredForIssueCount));

	}

	/**
	 * Method to Calculate the Average Closure Rate
	 * 
	 * @param fieldMapping
	 *            fieldMapping
	 * @param startLocalDate
	 *            startDate
	 * @param completedReleaseMap
	 *            Map<LocalDate, List<JiraIssue>>
	 * @return Map of Avg Issue Count, Story Point
	 */
	private Map<String, Object> getAverageData(FieldMapping fieldMapping, LocalDate startLocalDate,
			Map<LocalDate, List<JiraIssue>> completedReleaseMap) {
		Map<String, Object> averageDataMap = new HashMap<>();
		double avgIssueCount;
		double avgStoryPoint;
		int countOfDaysTillToday = 0;

		// count of days from where user want to cal avg
		Integer startDateCount = Optional.ofNullable(fieldMapping.getStartDateCountKPI150()).orElse(0);
		LocalDate predictionStartDate = startLocalDate.plusDays(startDateCount);

		LocalDate currentDate = predictionStartDate;
		List<JiraIssue> completedIssuesTillToday = new ArrayList<>();
		// completed issue between prediction start date and today both inclusive
		while (!currentDate.isBefore(predictionStartDate) && !LocalDate.now().isBefore(currentDate)) {
			completedIssuesTillToday.addAll(completedReleaseMap.getOrDefault(currentDate, new ArrayList<>()));
			currentDate = currentDate.plusDays(1);
			countOfDaysTillToday++;
		}
		// calculate the avg issue count and story point
		if (countOfDaysTillToday != 0 && CollectionUtils.isNotEmpty(completedIssuesTillToday)) {
			avgIssueCount = (double) completedIssuesTillToday.size() / countOfDaysTillToday;
			avgStoryPoint = getStoryPoint(completedIssuesTillToday, fieldMapping) / countOfDaysTillToday;
			averageDataMap.put(AVG_ISSUE_COUNT, roundingOff(avgIssueCount));
			averageDataMap.put(AVG_STORY_POINT, roundingOff(avgStoryPoint));
		}
		return averageDataMap;
	}

	/**
	 * Method for setting Trend value & Excel Data
	 * 
	 * @param kpiElement
	 *            kpiElement
	 * @param excelData
	 *            List<KPIExcelData>
	 * @param iterationKpiValueList
	 *            List<IterationKpiValue>
	 * @param issueCountDataGroup
	 *            List<DataCountGroup>
	 * @param issueSizeCountDataGroup
	 *            List<DataCountGroup>
	 */
	private void createExcelDataAndTrendValueList(KpiElement kpiElement, List<KPIExcelData> excelData,
			List<IterationKpiValue> iterationKpiValueList, List<DataCountGroup> issueCountDataGroup,
			List<DataCountGroup> issueSizeCountDataGroup) {
		if (CollectionUtils.isNotEmpty(issueCountDataGroup)) {
			IterationKpiValue kpiValueIssueCount = new IterationKpiValue();
			kpiValueIssueCount.setDataGroup(issueCountDataGroup);
			kpiValueIssueCount.setFilter1(ISSUE_COUNT);
			IterationKpiValue kpiValueSizeCount = new IterationKpiValue();
			kpiValueSizeCount.setDataGroup(issueSizeCountDataGroup);
			kpiValueSizeCount.setFilter1(STORY_POINT);
			iterationKpiValueList.add(kpiValueSizeCount);
			iterationKpiValueList.add(kpiValueIssueCount);

			kpiElement.setModalHeads(KPIExcelColumn.RELEASE_BURNUP.getColumns());
			kpiElement.setExcelColumns(KPIExcelColumn.RELEASE_BURNUP.getColumns());
			kpiElement.setExcelData(excelData);
		}
	}

	/**
	 * issue completed before the release version but happened to be present in
	 * selected release then issues to be present on the first day of release start
	 * date
	 */
	private Map<LocalDate, List<JiraIssue>> prepareCompletedIssueMap(
			Map<LocalDate, List<JiraIssue>> completedReleaseMap, LocalDate startLocalDate) {
		Map<LocalDate, List<JiraIssue>> rangedCompletedMap = new HashMap<>();
		completedReleaseMap.forEach((date, issues) -> {
			if (!date.isAfter(startLocalDate)) {
				rangedCompletedMap.computeIfPresent(startLocalDate, (key, value) -> {
					value.addAll(issues);
					return value;
				});
				rangedCompletedMap.putIfAbsent(startLocalDate, issues);
			} else {
				rangedCompletedMap.put(date, issues);
			}
		});
		return rangedCompletedMap;
	}

	/**
	 * Method for calculation x-axis duration & range
	 * 
	 * @param startLocalDate
	 *            startDate
	 * @param endLocalDate
	 *            endDate
	 * @return Map<String, Long>
	 */
	private Map<String, Long> getDurationRangeMap(LocalDate startLocalDate, LocalDate endLocalDate) {
		Map<String, Long> map = new HashMap<>();
		long range;
		String duration;
		// representing in months if week count > 120
		if (ChronoUnit.WEEKS.between(Objects.requireNonNull(startLocalDate), endLocalDate) > DAYS_RANGE) {
			range = ChronoUnit.MONTHS.between(Objects.requireNonNull(startLocalDate), endLocalDate) + 1;
			duration = CommonConstant.MONTH;
		}
		// added+1 to add the end date as well
		else if (ChronoUnit.DAYS.between(Objects.requireNonNull(startLocalDate), endLocalDate) + 1 > DAYS_RANGE) {
			range = ChronoUnit.WEEKS.between(Objects.requireNonNull(startLocalDate), endLocalDate) + 1;
			duration = CommonConstant.WEEK;
		} else {
			range = DAYS_RANGE;
			duration = CommonConstant.DAYS;
		}
		map.put(duration, range);
		return map;
	}

	/**
	 * Method for population of Release Scope,Progress & Prediction
	 * 
	 * @param filterWiseGroupedMap
	 *            Map<String, List<JiraIssue>>
	 * @param issueCount
	 *            DataCountGroup of Issue Count
	 * @param issueSize
	 *            DataCountGroup of Issue Size
	 * @param date
	 *            Date of x-axis
	 * @param duration
	 *            Duration
	 * @param fieldMapping
	 *            fieldMapping
	 * @param predictionDataMap
	 *            Map<String, Object>
	 */
	private void populateFilterWiseDataMap(Map<String, List<JiraIssue>> filterWiseGroupedMap, DataCountGroup issueCount,
			DataCountGroup issueSize, String date, String duration, FieldMapping fieldMapping,
			Map<String, Object> predictionDataMap) {
		List<DataCount> issueCountDataList = new ArrayList<>();
		List<DataCount> issueSizeDataList = new ArrayList<>();

		List<JiraIssue> overallIssues = filterWiseGroupedMap.getOrDefault(RELEASE_SCOPE, new ArrayList<>());
		List<JiraIssue> completedIssues = filterWiseGroupedMap.getOrDefault(RELEASE_PROGRESS, new ArrayList<>());

		double predictionIssueCount = (double) predictionDataMap.getOrDefault(ISSUE_COUNT_PREDICTION, 0d);
		double predictionIssueSize = (double) predictionDataMap.getOrDefault(ISSUE_SIZE_PREDICTION, 0d);
		boolean showPrediction = (boolean) predictionDataMap.getOrDefault(SHOW_PREDICTION, false);
		boolean isPredictionBoundary = (boolean) predictionDataMap.getOrDefault(IS_PREDICTION_BOUNDARY, false);

		createDataCount((long) overallIssues.size(), LINE_GRAPH_TYPE, RELEASE_SCOPE, issueCountDataList,
				CommonConstant.SOLID_LINE_TYPE);
		if (!showPrediction) {
			createDataCount((long) completedIssues.size(), LINE_GRAPH_TYPE, RELEASE_PROGRESS, issueCountDataList,
					CommonConstant.SOLID_LINE_TYPE);
		} else if (isPredictionBoundary) { // populating release Progress & prediction when boundary is reached
			createDataCount((long) completedIssues.size(), LINE_GRAPH_TYPE, RELEASE_PROGRESS, issueCountDataList,
					CommonConstant.SOLID_LINE_TYPE);
			createDataCount(predictionIssueCount, LINE_GRAPH_TYPE, RELEASE_PREDICTION, issueCountDataList,
					CommonConstant.DOTTED_LINE_TYPE);
		} else {
			createDataCount(predictionIssueCount, LINE_GRAPH_TYPE, RELEASE_PREDICTION, issueCountDataList,
					CommonConstant.DOTTED_LINE_TYPE);

		}
		issueCount.setFilter(date);
		issueCount.setDuration(duration);
		issueCount.setValue(issueCountDataList);

		createDataCount(getStoryPoint(overallIssues, fieldMapping), LINE_GRAPH_TYPE, RELEASE_SCOPE, issueSizeDataList,
				CommonConstant.SOLID_LINE_TYPE);
		if (!showPrediction) {
			createDataCount(getStoryPoint(completedIssues, fieldMapping), LINE_GRAPH_TYPE, RELEASE_PROGRESS,
					issueSizeDataList, CommonConstant.SOLID_LINE_TYPE);
		} else if (isPredictionBoundary) {// populating release Progress & prediction when boundary is reached
			createDataCount(getStoryPoint(completedIssues, fieldMapping), LINE_GRAPH_TYPE, RELEASE_PROGRESS,
					issueSizeDataList, CommonConstant.SOLID_LINE_TYPE);
			createDataCount(predictionIssueSize, LINE_GRAPH_TYPE, RELEASE_PREDICTION, issueSizeDataList,
					CommonConstant.DOTTED_LINE_TYPE);
		} else {
			createDataCount(predictionIssueSize, LINE_GRAPH_TYPE, RELEASE_PREDICTION, issueSizeDataList,
					CommonConstant.DOTTED_LINE_TYPE);
		}
		issueSize.setFilter(date);
		issueSize.setDuration(duration);
		issueSize.setValue(issueSizeDataList);
	}

	/**
	 * Get Sum of StoryPoint for List of JiraIssue
	 * 
	 * @param jiraIssueList
	 *            List<JiraIssue>
	 * @param fieldMapping
	 *            fieldMapping
	 * @return Sum of Story Point
	 */
	Double getStoryPoint(List<JiraIssue> jiraIssueList, FieldMapping fieldMapping) {
		Double ticketEstimate = 0.0d;
		if (CollectionUtils.isNotEmpty(jiraIssueList)) {
			if (StringUtils.isNotEmpty(fieldMapping.getEstimationCriteria())
					&& fieldMapping.getEstimationCriteria().equalsIgnoreCase(CommonConstant.STORY_POINT)) {
				ticketEstimate = jiraIssueList.stream()
						.mapToDouble(ji -> Optional.ofNullable(ji.getStoryPoints()).orElse(0.0d)).sum();
			} else {
				double totalOriginalEstimate = jiraIssueList.stream()
						.mapToDouble(jiraIssue -> Optional.ofNullable(jiraIssue.getOriginalEstimateMinutes()).orElse(0))
						.sum();
				double inHours = totalOriginalEstimate / 60;
				ticketEstimate = inHours / fieldMapping.getStoryPointToHourMapping();
			}
		}
		return roundingOff(ticketEstimate);

	}

	/**
	 * Method to Populate the DataCount Object
	 * 
	 * @param value
	 *            value
	 * @param graphType
	 *            graphType
	 * @param label
	 *            Label
	 * @param issueCountDataList
	 *            List<DataCount>
	 * @param lineCategory
	 *            Line Type
	 */
	private void createDataCount(Object value, String graphType, String label, List<DataCount> issueCountDataList,
			String lineCategory) {
		DataCount dataCount = new DataCount();
		dataCount.setData(String.valueOf(value));
		dataCount.setKpiGroup(label);
		dataCount.setValue(value);
		dataCount.setGraphType(graphType);
		dataCount.setLineCategory(lineCategory);
		issueCountDataList.add(dataCount);
	}

	/**
	 * Method to get Release Scope,Progress in a DateRange
	 * 
	 * @param dateRange
	 *            CustomDateRange
	 * @param addedIssuesMap
	 *            Map<LocalDate, List<JiraIssue>>
	 * @param removeIssueMap
	 *            Map<LocalDate, List<JiraIssue>>
	 * @param fullReleaseIssueMap
	 *            Map<LocalDate, List<JiraIssue>>
	 * @param overallIssues
	 *            List<JiraIssue>
	 * @param completedReleaseMap
	 *            Map<LocalDate, List<JiraIssue>>
	 * @param overallCompletedIssues
	 *            List<JiraIssue>
	 * @return Map<String, List<JiraIssue>>
	 */
	private Map<String, List<JiraIssue>> createFilterWiseGroupedMap(CustomDateRange dateRange,
			Map<LocalDate, List<JiraIssue>> addedIssuesMap, Map<LocalDate, List<JiraIssue>> removeIssueMap,
			Map<LocalDate, List<JiraIssue>> fullReleaseIssueMap, List<JiraIssue> overallIssues,
			Map<LocalDate, List<JiraIssue>> completedReleaseMap, List<JiraIssue> overallCompletedIssues) {
		Map<String, List<JiraIssue>> groupedMap = new HashMap<>();
		List<JiraIssue> defaultIssues = new ArrayList<>();
		List<JiraIssue> allAddedIssues = new ArrayList<>();
		List<JiraIssue> removedIssues = new ArrayList<>();
		List<JiraIssue> completedIssues = new ArrayList<>();

		for (LocalDate currentDate = dateRange.getStartDate(); currentDate.compareTo(dateRange.getStartDate()) >= 0
				&& dateRange.getEndDate().compareTo(currentDate) >= 0; currentDate = currentDate.plusDays(1)) {
			if (currentDate.isEqual(tempStartDate)) {
				defaultIssues.addAll(fullReleaseIssueMap.getOrDefault(currentDate, new ArrayList<>()));// defaultMap
				defaultIssues.addAll(addedIssuesMap.getOrDefault(currentDate, new ArrayList<>()));
				defaultIssues.removeAll(removeIssueMap.getOrDefault(currentDate, new ArrayList<>()));
			} else {
				defaultIssues.addAll(fullReleaseIssueMap.getOrDefault(currentDate, new ArrayList<>()));// defaultMap
				allAddedIssues.addAll(addedIssuesMap.getOrDefault(currentDate, new ArrayList<>()));
				removedIssues.addAll(removeIssueMap.getOrDefault(currentDate, new ArrayList<>()));
			}
			completedIssues.addAll(completedReleaseMap.getOrDefault(currentDate, new ArrayList<>()));
		}

		// if on same day issue is added to release and removed from release, then on
		// same day delete that issue from
		// removed
		List<JiraIssue> commonIssues = (List<JiraIssue>) CollectionUtils.intersection(allAddedIssues, removedIssues);
		removedIssues.removeAll(commonIssues);
		allAddedIssues.removeAll(commonIssues);
		List<JiraIssue> commonIssuesRemoved = (List<JiraIssue>) CollectionUtils.intersection(overallIssues,
				removedIssues);
		allAddedIssues.removeAll(overallIssues);
		groupedMap.put(SCOPE_REMOVED, commonIssuesRemoved);
		groupedMap.put(SCOPE_ADDED, allAddedIssues);

		overallIssues.addAll(defaultIssues);
		overallIssues.addAll(allAddedIssues);
		overallIssues.removeAll(removedIssues);
		overallIssues = overallIssues.stream().distinct().collect(Collectors.toList());

		// issues which were completed within Release duration, but were out of selected
		// release when completed
		overallCompletedIssues.addAll(completedIssues);
		List<JiraIssue> allCompletedIssuesOutOfOverall = new ArrayList<>(overallCompletedIssues);
		// out of the overallIssues, what all issues were completed
		allCompletedIssuesOutOfOverall.retainAll(overallIssues);
		groupedMap.put(RELEASE_SCOPE, overallIssues);
		groupedMap.put(RELEASE_PROGRESS, allCompletedIssuesOutOfOverall);
		groupedMap.put(OVERALL_COMPLETED, overallCompletedIssues.stream().distinct().collect(Collectors.toList()));

		return groupedMap;
	}

	/**
	 * Method to Get Date on basis of range for x-axis representation
	 * 
	 * @param dateRange
	 *            CustomDateRange
	 * @param range
	 *            range
	 * @return x-axis representation value
	 */
	private String getRange(CustomDateRange dateRange, String range) {
		String date = null;
		if (range.equalsIgnoreCase(CommonConstant.MONTH)) {
			LocalDate dateValue = dateRange.getStartDate();
			date = dateValue.getYear() + Constant.DASH + dateValue.getMonthValue();
		} else if (range.equalsIgnoreCase(CommonConstant.WEEK)) {
			LocalDate endDate = dateRange.getEndDate();
			while (!endDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
				endDate = endDate.minusDays(1);
			}
			date = DateUtil.dateTimeConverter(dateRange.getStartDate().toString(), DateUtil.DATE_FORMAT,
					DateUtil.DISPLAY_DATE_FORMAT) + " to "
					+ DateUtil.dateTimeConverter(endDate.toString(), DateUtil.DATE_FORMAT,
							DateUtil.DISPLAY_DATE_FORMAT);
		} else {
			date = dateRange.getStartDate().toString();
		}
		return date;
	}

	/**
	 * Method to Populate Excel Data Object
	 * 
	 * @param requestTrackerId
	 *            request Tracker id
	 * @param excelData
	 *            List<KPIExcelData>
	 * @param jiraIssueList
	 *            List<JiraIssue>
	 * @param fieldMapping
	 *            fieldMapping
	 */
	private void populateExcelDataObject(String requestTrackerId, List<KPIExcelData> excelData,
			List<JiraIssue> jiraIssueList, FieldMapping fieldMapping) {
		if (requestTrackerId.toLowerCase().contains(KPISource.EXCEL.name().toLowerCase())
				&& CollectionUtils.isNotEmpty(jiraIssueList)) {
			KPIExcelUtility.populateReleaseDefectRelatedExcelData(jiraIssueList, excelData, fieldMapping);
		}
	}

	/**
	 * Get JiraIssue for respective CustomHistory
	 * 
	 * @param totalIssueList
	 *            List<JiraIssue>
	 * @param issueHistory
	 *            issueHistory
	 * @return List<JiraIssue>
	 */
	private List<JiraIssue> getRespectiveJiraIssue(List<JiraIssue> totalIssueList,
			JiraIssueCustomHistory issueHistory) {
		return totalIssueList.stream()
				.filter(jiraIssue -> jiraIssue.getNumber().equalsIgnoreCase(issueHistory.getStoryID()))
				.collect(Collectors.toList());
	}

	/**
	 * Get Next Date of population based on duration
	 * 
	 * @param duration
	 *            duration
	 * @param currentDate
	 *            currDate
	 * @return LocalDate
	 */
	private LocalDate getNextRangeDate(String duration, LocalDate currentDate) {
		if (duration.equalsIgnoreCase(CommonConstant.MONTH)) {
			currentDate = currentDate.plusMonths(1);
		} else if (duration.equalsIgnoreCase(CommonConstant.WEEK)) {
			currentDate = currentDate.plusWeeks(1);
		} else {
			currentDate = currentDate.plusDays(1);
		}
		return currentDate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getQualifierType() {
		return KPICode.RELEASE_BURNUP.name();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer calculateKPIMetrics(Map<String, Object> stringObjectMap) {
		return null;
	}

}
