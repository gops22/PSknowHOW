package com.publicissapient.kpidashboard.apis.bitbucket.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.publicissapient.kpidashboard.apis.util.KpiDataHelper;
import com.publicissapient.kpidashboard.common.constant.CommonConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publicissapient.kpidashboard.apis.appsetting.service.ConfigHelperService;
import com.publicissapient.kpidashboard.apis.config.CustomApiConfig;
import com.publicissapient.kpidashboard.apis.constant.Constant;
import com.publicissapient.kpidashboard.apis.enums.Filters;
import com.publicissapient.kpidashboard.apis.enums.KPICode;
import com.publicissapient.kpidashboard.apis.enums.KPIExcelColumn;
import com.publicissapient.kpidashboard.apis.enums.KPISource;
import com.publicissapient.kpidashboard.apis.errors.ApplicationException;
import com.publicissapient.kpidashboard.apis.model.*;
import com.publicissapient.kpidashboard.apis.repotools.model.Branches;
import com.publicissapient.kpidashboard.apis.repotools.model.RepoToolKpiMetricResponse;
import com.publicissapient.kpidashboard.apis.repotools.service.RepoToolsConfigServiceImpl;
import com.publicissapient.kpidashboard.apis.util.KPIExcelUtility;
import com.publicissapient.kpidashboard.common.model.application.DataCount;
import com.publicissapient.kpidashboard.common.model.application.DataCountGroup;
import com.publicissapient.kpidashboard.common.model.application.Tool;
import com.publicissapient.kpidashboard.common.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PickupTimeServiceImpl extends BitBucketKPIService<Double, List<Object>, Map<String, Object>> {

	private static final String REPO_TOOLS = "RepoTool";
	public static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String PICKUP_TIME_KPI = "pickup-time-bulk/";
	public static final String MR_COUNT = "No of MRs";
	public static final String WEEK_FREQUENCY = "week";
	public static final String DAY_FREQUENCY = "day";

	@Autowired
	private ConfigHelperService configHelperService;

	@Autowired
	private CustomApiConfig customApiConfig;

	@Autowired
	private RepoToolsConfigServiceImpl repoToolsConfigService;

	@Override
	public String getQualifierType() {
		return KPICode.PICKUP_TIME.name();
	}

	@Override
	public KpiElement getKpiData(KpiRequest kpiRequest, KpiElement kpiElement,
			TreeAggregatorDetail treeAggregatorDetail) throws ApplicationException {
		Node root = treeAggregatorDetail.getRoot();
		Map<String, Node> mapTmp = treeAggregatorDetail.getMapTmp();

		treeAggregatorDetail.getMapOfListOfProjectNodes().forEach((k, v) -> {
			if (Filters.getFilter(k) == Filters.PROJECT) {
				projectWiseLeafNodeValue(kpiElement, kpiRequest, mapTmp, v);
			}

		});

		log.debug("[PROJECT-WISE][{}]. Values of leaf node after KPI calculation {}", kpiRequest.getRequestTrackerId(),
				root);

		Map<Pair<String, String>, Node> nodeWiseKPIValue = new HashMap<>();
		calculateAggregatedValueMap(root, nodeWiseKPIValue, KPICode.PICKUP_TIME);

		Map<String, List<DataCount>> trendValuesMap = getTrendValuesMap(kpiRequest, nodeWiseKPIValue,
				KPICode.PICKUP_TIME);
		Map<String, Map<String, List<DataCount>>> kpiFilterWiseProjectWiseDc = new LinkedHashMap<>();
		trendValuesMap.forEach((issueType, dataCounts) -> {
			Map<String, List<DataCount>> projectWiseDc = dataCounts.stream()
					.collect(Collectors.groupingBy(DataCount::getData));
			kpiFilterWiseProjectWiseDc.put(issueType, projectWiseDc);
		});

		List<DataCountGroup> dataCountGroups = new ArrayList<>();
		kpiFilterWiseProjectWiseDc.forEach((issueType, projectWiseDc) -> {
			DataCountGroup dataCountGroup = new DataCountGroup();
			List<DataCount> dataList = new ArrayList<>();
			projectWiseDc.entrySet().stream().forEach(trend -> dataList.addAll(trend.getValue()));
			dataCountGroup.setFilter(issueType);
			dataCountGroup.setValue(dataList);
			dataCountGroups.add(dataCountGroup);
		});
		kpiElement.setTrendValueList(dataCountGroups);
		return kpiElement;
	}

	private void projectWiseLeafNodeValue(KpiElement kpiElement, KpiRequest kpiRequest, Map<String, Node> mapTmp,
			List<Node> projectLeafNodeList) {

		CustomDateRange dateRange = KpiDataHelper.getStartAndEndDate(kpiRequest);
		String requestTrackerId = getRequestTrackerId();
		LocalDate localEndDate = dateRange.getEndDate();

		Integer dataPoints = NumberUtils.isCreatable(kpiRequest.getIds()[0])?Integer.parseInt(kpiRequest.getIds()[0]):5;
		String duration = kpiRequest.getSelectedMap().get(CommonConstant.date).get(0);

		// gets the tool configuration
		Map<ObjectId, Map<String, List<Tool>>> toolMap = configHelperService.getToolItemMap();
		List<RepoToolKpiMetricResponse> repoToolKpiMetricResponseList = getRepoToolsKpiMetricResponse(localEndDate,
				toolMap, projectLeafNodeList, dataPoints, duration);

		List<KPIExcelData> excelData = new ArrayList<>();
		projectLeafNodeList.stream().forEach(node -> {
			ProjectFilter accountHierarchyData = node.getProjectFilter();
			ObjectId configId = accountHierarchyData == null ? null : accountHierarchyData.getBasicProjectConfigId();
			Map<String, List<Tool>> mapOfListOfTools = toolMap.get(configId);
			List<Tool> reposList = new ArrayList<>();
			populateRepoList(reposList, mapOfListOfTools);
			if (CollectionUtils.isEmpty(reposList)) {
				log.error("[BITBUCKET-AGGREGATED-VALUE]. No Jobs found for this project {}", node.getProjectFilter());
				return;
			}

			List<Map<String, Double>> repoWisePickupTimeList = new ArrayList<>();
			List<String> repoList = new ArrayList<>();
			List<String> branchList = new ArrayList<>();
			String projectName = node.getProjectFilter().getName();
			Map<String, List<DataCount>> aggDataMap = new HashMap<>();
			Map<String, Double> aggPickupTimeForRepo = new HashMap<>();
			Map<String, Integer> aggMRCount = new HashMap<>();
			reposList.forEach(repo -> {
				if (!CollectionUtils.isEmpty(repo.getProcessorItemList())
						&& repo.getProcessorItemList().get(0).getId() != null) {
					Map<String, Double> excelDataLoader = new HashMap<>();
					if (CollectionUtils.isNotEmpty(repoToolKpiMetricResponseList)) {
						String branchName = getBranchSubFilter(repo, projectName);
						Map<String, Double> dateWisePickupTime = new HashMap<>();
						Map<String, Integer> dateWiseMRCount = new HashMap<>();
						createDateLabelWiseMap(repoToolKpiMetricResponseList, repo.getRepositoryName(),
								repo.getBranch(), dateWisePickupTime, dateWiseMRCount);
						aggPickupTime(aggPickupTimeForRepo, dateWisePickupTime, aggMRCount, dateWiseMRCount);
						setWeekWisePickupTime(dateWisePickupTime, dateWiseMRCount, excelDataLoader, branchName,
								projectName, aggDataMap, duration, dataPoints);
					}
					repoWisePickupTimeList.add(excelDataLoader);
					repoList.add(repo.getUrl());
					branchList.add(repo.getBranch());

				}
			});
			setWeekWisePickupTime(aggPickupTimeForRepo, aggMRCount, new HashMap<>(), Constant.AGGREGATED_VALUE,
					projectName, aggDataMap, duration, dataPoints);
			mapTmp.get(node.getId()).setValue(aggDataMap);

			populateExcelDataObject(requestTrackerId, repoWisePickupTimeList, repoList, branchList, excelData, node);
		});
		kpiElement.setExcelData(excelData);
		kpiElement.setExcelColumns(KPIExcelColumn.PICKUP_TIME.getColumns());
	}

	private void aggPickupTime(Map<String, Double> aggPickupTimeForRepo, Map<String, Double> pickupTimeForRepo,
			Map<String, Integer> aggMRCount, Map<String, Integer> mrCount) {
		if (MapUtils.isNotEmpty(pickupTimeForRepo)) {
			aggPickupTimeForRepo.putAll(pickupTimeForRepo);
		}
		if (MapUtils.isNotEmpty(mrCount)) {
			aggMRCount.putAll(mrCount);
		}
	}

	private void populateRepoList(List<Tool> reposList, Map<String, List<Tool>> mapOfListOfTools) {
		if (null != mapOfListOfTools) {
			reposList.addAll(mapOfListOfTools.get(REPO_TOOLS) == null ? Collections.emptyList()
					: mapOfListOfTools.get(REPO_TOOLS));
		}
	}

	private void createDateLabelWiseMap(List<RepoToolKpiMetricResponse> repoToolKpiMetricResponsesCommit,
			String repoName, String branchName, Map<String, Double> dateWisePickupTime,
			Map<String, Integer> dateWiseMRCount) {

		for (RepoToolKpiMetricResponse response : repoToolKpiMetricResponsesCommit) {
			if (response.getRepositories() != null) {
				Optional<Branches> matchingBranch = response.getRepositories().stream()
						.filter(repository -> repository.getName().equals(repoName))
						.flatMap(repository -> repository.getBranches().stream())
						.filter(branch -> branch.getName().equals(branchName)).findFirst();
				double pickupTime = matchingBranch.isPresent() ? matchingBranch.get().getHours() : 0.0d;
				int mrCount = matchingBranch.isPresent() ? matchingBranch.get().getMergeRequestsPT().size() : 0;
				dateWisePickupTime.put(response.getDateLabel(), pickupTime);
				dateWiseMRCount.put(response.getDateLabel(), mrCount);
			}
		}
	}

	private void setWeekWisePickupTime(Map<String, Double> weekWisePickupTime, Map<String, Integer> weekWiseMRCount,
			Map<String, Double> excelDataLoader, String branchName, String projectName,
			Map<String, List<DataCount>> aggDataMap, String duration, Integer dataPoints) {
		LocalDate currentDate = LocalDate.now();
		for (int i = 0; i < dataPoints; i++) {
			CustomDateRange dateRange = KpiDataHelper.getStartAndEndDateForDataFiltering(currentDate, duration);
			double pickupTime = Double.parseDouble(
					decimalFormat.format(weekWisePickupTime.getOrDefault(dateRange.getStartDate().toString(), 0.0d)));
			String date = getDateRange(dateRange, duration);
			aggDataMap.putIfAbsent(branchName, new ArrayList<>());
			DataCount dataCount = setDataCount(projectName, date, pickupTime,
					weekWiseMRCount.getOrDefault(dateRange.getStartDate().toString(), 0).longValue());
			aggDataMap.get(branchName).add(dataCount);
			excelDataLoader.put(date, pickupTime);
			currentDate = getNextRangeDate(duration, currentDate);

		}

	}

	private String getDateRange(CustomDateRange dateRange, String duration) {
		String range = null;
		if (CommonConstant.WEEK.equalsIgnoreCase(duration)) {
			range = DateUtil.dateTimeConverter(dateRange.getStartDate().toString(), DateUtil.DATE_FORMAT,
					DateUtil.DISPLAY_DATE_FORMAT) + " to "
					+ DateUtil.dateTimeConverter(dateRange.getEndDate().toString(), DateUtil.DATE_FORMAT,
							DateUtil.DISPLAY_DATE_FORMAT);
		} else {
			range = dateRange.getStartDate().toString();
		}
		return range;
	}

	private LocalDate getNextRangeDate(String duration, LocalDate currentDate) {
		if ((CommonConstant.WEEK).equalsIgnoreCase(duration)) {
			currentDate = currentDate.minusWeeks(1);
		} else {
			currentDate = currentDate.minusDays(1);
		}
		return currentDate;
	}

	private DataCount setDataCount(String projectName, String week, Double value, Long mrCount) {
		Map<String, Object> hoverMap = new HashMap<>();
		hoverMap.put(MR_COUNT, mrCount);
		DataCount dataCount = new DataCount();
		dataCount.setData(String.valueOf(value == null ? 0L : value.longValue()));
		dataCount.setSProjectName(projectName);
		dataCount.setDate(week);
		dataCount.setHoverValue(hoverMap);
		dataCount.setValue(value == null ? 0.0 : value.longValue());
		return dataCount;
	}

	private List<RepoToolKpiMetricResponse> getRepoToolsKpiMetricResponse(LocalDate endDate,
			Map<ObjectId, Map<String, List<Tool>>> toolMap, List<Node> nodeList, Integer dataPoint, String duration) {

		List<String> projectCodeList = new ArrayList<>();
		nodeList.forEach(node -> {
			ProjectFilter accountHierarchyData = node.getProjectFilter();
			ObjectId configId = accountHierarchyData == null ? null : accountHierarchyData.getBasicProjectConfigId();
			List<Tool> tools = toolMap.getOrDefault(configId, Collections.emptyMap()).getOrDefault(REPO_TOOLS,
					Collections.emptyList());
			if (!CollectionUtils.isEmpty(tools)) {
				projectCodeList.add(node.getId());
			}
		});

		List<RepoToolKpiMetricResponse> repoToolKpiMetricResponseList = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(projectCodeList)) {
			LocalDate startDate = LocalDate.now().minusDays(dataPoint);
			if (duration.equalsIgnoreCase(CommonConstant.WEEK)) {
				startDate = LocalDate.now().minusWeeks(dataPoint);
			}
			String debbieDuration = duration.equalsIgnoreCase(CommonConstant.WEEK)?WEEK_FREQUENCY:DAY_FREQUENCY;
			repoToolKpiMetricResponseList = repoToolsConfigService.getRepoToolKpiMetrics(projectCodeList,
					PICKUP_TIME_KPI, startDate.toString(), endDate.toString(), debbieDuration);
		}

		return repoToolKpiMetricResponseList;
	}

	private void populateExcelDataObject(String requestTrackerId, List<Map<String, Double>> repoWiseMRList,
			List<String> repoList, List<String> branchList, List<KPIExcelData> validationDataMap, Node node) {
		if (requestTrackerId.toLowerCase().contains(KPISource.EXCEL.name().toLowerCase())) {

			String projectName = node.getProjectFilter().getName();

			KPIExcelUtility.populatePickupTimeExcelData(projectName, repoWiseMRList, repoList, branchList,
					validationDataMap);

		}
	}

	@Override
	public Double calculateKPIMetrics(Map<String, Object> stringObjectMap) {
		return null;
	}

	@Override
	public Double calculateKpiValue(List<Double> valueList, String kpiName) {
		return calculateKpiValueForDouble(valueList, kpiName);
	}

	@Override
	public Map<String, Object> fetchKPIDataFromDb(List<Node> leafNodeList, String startDate, String endDate,
			KpiRequest kpiRequest) {
		return null;
	}
}
