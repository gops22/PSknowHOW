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

package com.publicissapient.kpidashboard.common.model.application.dto;//NOPMD

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;

import com.publicissapient.kpidashboard.common.constant.CommonConstant;
import com.publicissapient.kpidashboard.common.model.application.AdditionalFilterConfig;
import com.publicissapient.kpidashboard.common.model.generic.BasicModel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Field mapping. Represents Jira field mapping values
 */
@SuppressWarnings("PMD.TooManyFields")
@Getter
@Setter
public class FieldMappingDTO extends BasicModel {

	private ObjectId projectToolConfigId;
	private ObjectId basicProjectConfigId;
	private String projectId;
	private String atmQueryEndpoint;
	private String sprintName;
	private String epicName;
	private List<String> jiradefecttype;
	private String epicLink;
	private List<String> jiraSubTaskDefectType;

	// defectPriority
	private List<String> defectPriority;
	private List<String> defectPriorityKPI135;
	private List<String> defectPriorityKPI14;
	private List<String> defectPriorityQAKPI111;
	private List<String> defectPriorityKPI82;
	private List<String> defectPriorityKPI133;

	private String[] jiraIssueTypeNames;
	private String[] jiraIssueTypeNamesAVR;
	private String storyFirstStatus;
	private String storyFirstStatusKPI148;
	private String storyFirstStatusKPI3;
	private String[] linkDefectToStoryField;
	private String rootCause;
	private List<String> jiraStatusForDevelopment;
	private List<String> jiraStatusForDevelopmentAVR;
	private List<String> jiraStatusForDevelopmentKPI82;
	private List<String> jiraStatusForDevelopmentKPI135;
	private String jiraAtmProjectId;
	private String jiraAtmProjectKey;
	private List<String> jiraIssueEpicType;

	private List<String> jiraStatusForQa;
	private List<String> jiraStatusForQaKPI135;
	private List<String> jiraStatusForQaKPI82;
	private List<String> jiraStatusForQaKPI148;
	// type of test cases
	private List<String> jiraDefectInjectionIssueType;
	private List<String> jiraDefectInjectionIssueTypeKPI14;
	private List<String> jiraDod;
	private List<String> jiraDodKPI152;
	private List<String> jiraDodKPI151;
	private List<String> jiraDodKPI14;
	private List<String> jiraDodQAKPI111;
	private List<String> jiraDodKPI3;
	private List<String> jiraDodKPI127;
	private List<String> jiraDodKPI37;

	private String jiraDefectCreatedStatus;
	private String jiraDefectCreatedStatusKPI14;
	private List<String> jiraTechDebtIssueType;
	private String jiraTechDebtIdentification;
	private String jiraTechDebtCustomField;
	private List<String> jiraTechDebtValue;

	private String jiraDefectRejectionStatus;
	private String jiraDefectRejectionStatusKPI152;
	private String jiraDefectRejectionStatusKPI151;
	private String jiraDefectRejectionStatusAVR;
	private String jiraDefectRejectionStatusKPI28;
	private String jiraDefectRejectionStatusKPI37;
	private String jiraDefectRejectionStatusKPI35;
	private String jiraDefectRejectionStatusKPI82;
	private String jiraDefectRejectionStatusKPI135;
	private String jiraDefectRejectionStatusKPI133;
	private String jiraDefectRejectionStatusRCAKPI36;
	private String jiraDefectRejectionStatusKPI14;
	private String jiraDefectRejectionStatusQAKPI111;

	private String jiraBugRaisedByIdentification;
	private List<String> jiraBugRaisedByValue;

	private List<String> jiraDefectSeepageIssueType;
	private List<String> jiraIssueTypeKPI35;
	private String jiraBugRaisedByCustomField;
	private List<String> jiraDefectRemovalStatus;
	private List<String> jiraDefectRemovalStatusKPI34;
	private List<String> jiraDefectRemovalIssueType;
	// Added for Defect Reopen Rate KPI.
	private List<String> jiraDefectClosedStatus;
	private List<String> jiraDefectClosedStatusKPI137;


	private String jiraStoryPointsCustomField;
	// parent issue type for the test
	private List<String> jiraTestAutomationIssueType;
	// value of the automated test case Eg. Yes, Cannot Automate, No

	private List<String> jiraSprintVelocityIssueType;
	private List<String> jiraSprintVelocityIssueTypeKPI138;

	private List<String> jiraSprintCapacityIssueType;
	private List<String> jiraSprintCapacityIssueTypeKpi46;

	private List<String> jiraDefectRejectionlIssueType;

	private List<String> jiraDefectCountlIssueType;
	private List<String> jiraDefectCountlIssueTypeKPI28;
	private List<String> jiraDefectCountlIssueTypeKPI36;

	private List<String> jiraIssueDeliverdStatus;
	private List<String> jiraIssueDeliverdStatusKPI138;
	private List<String> jiraIssueDeliverdStatusAVR;
	private List<String> jiraIssueDeliverdStatusKPI126;
	private List<String> jiraIssueDeliverdStatusKPI82;

	private String readyForDevelopmentStatus;
	private List<String> readyForDevelopmentStatusKPI138;

private String jiraDor;
private List<String> jiraDorKPI3;

private List<String> jiraIntakeToDorIssueType;
private List<String> jiraIssueTypeKPI3;

private List<String> jiraStoryIdentification;
private List<String> jiraStoryIdentificationKPI129;
private List<String> jiraStoryIdentificationKpi40;
private List<String> jiraStoryIdentificationKPI164;

private String jiraLiveStatus;
private String jiraLiveStatusKPI152;
private String jiraLiveStatusKPI151;
private List<String> jiraLiveStatusKPI3;
private String jiraLiveStatusLTK;
private String jiraLiveStatusNOPK;
private String jiraLiveStatusNOSK;
private String jiraLiveStatusNORK;
private String jiraLiveStatusOTA;
private String jiraLiveStatusKPI127;

	private List<String> ticketCountIssueType;

	private List<String> kanbanRCACountIssueType;

	private List<String> jiraTicketVelocityIssueType;

	private List<String> ticketDeliverdStatus;

	private List<String> ticketReopenStatus;

	private List<String> kanbanJiraTechDebtIssueType;

	private List<String> jiraTicketResolvedStatus;
	private List<String> jiraTicketClosedStatus;
	private List<String> kanbanCycleTimeIssueType;
	private List<String> jiraTicketTriagedStatus;
	private List<String> jiraTicketWipStatus;
	private List<String> jiraTicketRejectedStatus;

	private String jiraStatusMappingCustomField;
	private List<String> excludeStatusKpi129;

	private List<String> rootCauseValue;
	private List<String> excludeRCAFromFTPR; // test done
	private List<String> excludeRCAFromKPI82;
	private List<String> excludeRCAFromKPI135;
	private List<String> excludeRCAFromKPI14;
	private List<String> excludeRCAFromQAKPI111;
	private List<String> excludeRCAFromKPI133;

	private Boolean pickNewATMJIRADetails;

	private List<String> jiraDorToLiveIssueType;
	private List<String> jiraProductiveStatus;

	private List<String> jiraCommitmentReliabilityIssueType;

	private List<String> resolutionTypeForRejection;
	private List<String> resolutionTypeForRejectionAVR;
	private List<String> resolutionTypeForRejectionKPI28;
	private List<String> resolutionTypeForRejectionKPI37;
	private List<String> resolutionTypeForRejectionKPI35;
	private List<String> resolutionTypeForRejectionKPI82;
	private List<String> resolutionTypeForRejectionKPI135;
	private List<String> resolutionTypeForRejectionKPI133;
	private List<String> resolutionTypeForRejectionRCAKPI36;
	private List<String> resolutionTypeForRejectionKPI14;
	private List<String> resolutionTypeForRejectionQAKPI111;

	private List<String> jiraQADefectDensityIssueType;
	private List<String> jiraQAKPI111IssueType;
	private List<String> jiraItrQSIssueTypeKPI133;

	private String jiraBugRaisedByQACustomField;
	private String jiraBugRaisedByQAIdentification;
	private List<String> jiraBugRaisedByQAValue;
	private List<String> jiraDefectDroppedStatus;
	private List<String> jiraDefectDroppedStatusKPI127;


	// Epic custom Field mapping
	private String epicCostOfDelay;
	private String epicRiskReduction;
	private String epicUserBusinessValue;
	private String epicWsjf;
	private String epicTimeCriticality;
	private String epicJobSize;
	private String epicPlannedValue;
	private String epicAchievedValue;

	private String atmSubprojectField;

	// Squad Mapping
	private String squadIdentifier;
	private List<String> squadIdentMultiValue;
	private String squadIdentSingleValue;

	// Production Defect Mapping
	private String productionDefectCustomField;
	private String productionDefectIdentifier;
	private List<String> productionDefectValue;
	private String productionDefectComponentValue;

	// issue status to exclude missing worklogs
	private List<String> issueStatusToBeExcludedFromMissingWorklogs;

	// field for In Progress status
	private List<String> jiraStatusForInProgress;
	private List<String> jiraStatusForInProgressKPI148;
	private List<String> jiraStatusForInProgressKPI122;
	private List<String> jiraStatusForInProgressKPI145;
	private List<String> jiraStatusForInProgressKPI125;
	private List<String> jiraStatusForInProgressKPI128;
	private List<String> jiraStatusForInProgressKPI123;
	private List<String> jiraStatusForInProgressKPI119;

	@Builder.Default
	private String estimationCriteria = "Story Point";

	@Builder.Default
	private Double storyPointToHourMapping = 8D;

	@Builder.Default
	private Double workingHoursDayCPT = 6D;

	// additional filter config fields
	private List<AdditionalFilterConfig> additionalFilterConfig;

	// issue status to exclude missing worklogs
	private List<String> issueStatusExcluMissingWork;
	private List<String> issueStatusExcluMissingWorkKPI124;

	// issue On Hold status to exclude Closure possible
	private List<String> jiraOnHoldStatus;

	// field for FTPR
	private List<String> jiraFTPRStoryIdentification;
	private List<String> jiraKPI82StoryIdentification;
	private List<String> jiraKPI135StoryIdentification;

	// field for Wasting - wait status
	private List<String> jiraWaitStatus;
	private List<String> jiraWaitStatusKPI131;

	// field for Wasting - block status
	private List<String> jiraBlockedStatus;
	private List<String> jiraBlockedStatusKPI131;

	// field for Wasting - Include Blocked Status
	private String jiraIncludeBlockedStatus;
	private String jiraIncludeBlockedStatusKPI131;

	// for for JiraDueDate
	@Builder.Default
	private String jiraDueDateField = CommonConstant.DUE_DATE;
	private String jiraDueDateCustomField;
	private String jiraDevDueDateField = CommonConstant.DEV_DUE_DATE;
	private String jiraDevDueDateCustomField;
	private List<String> jiraDevDoneStatus;
	private List<String> jiraDevDoneStatusKPI119;
	private List<String> jiraDevDoneStatusKPI145;
	private List<String> jiraDevDoneStatusKPI128;

	// For DTS_21154 - field for Team refinement status
	private List<String> jiraRejectedInRefinement;
	private List<String> jiraRejectedInRefinementKPI139;


	// For DTS_21154 - field for Stakeholder refinement status
	private List<String> jiraAcceptedInRefinement;
	private List<String> jiraAcceptedInRefinementKPI139;


	// For DTS_21154 - field for Stakeholder refinement status
	private List<String> jiraReadyForRefinement;
	private List<String> jiraReadyForRefinementKPI139;

	private List<String> jiraFtprRejectStatus;
	private List<String> jiraFtprRejectStatusKPI135;
	private List<String> jiraFtprRejectStatusKPI82;

	private List<String> jiraIterationCompletionStatusCustomField;
	private List<String> jiraIterationCompletionStatusKPI135;
	private List<String> jiraIterationCompletionStatusKPI122;
	private List<String> jiraIterationCompletionStatusKPI75;
	private List<String> jiraIterationCompletionStatusKPI145;
	private List<String> jiraIterationCompletionStatusKPI140;
	private List<String> jiraIterationCompletionStatusKPI132;
	private List<String> jiraIterationCompletionStatusKPI136;
	private List<String> jiraIterationCompletionStatusKpi72;
	private List<String> jiraIterationCompletionStatusKpi39;
	private List<String> jiraIterationCompletionStatusKpi5;
	private List<String> jiraIterationCompletionStatusKPI124;
	private List<String> jiraIterationCompletionStatusKPI123;
	private List<String> jiraIterationCompletionStatusKPI125;
	private List<String> jiraIterationCompletionStatusKPI120;
	private List<String> jiraIterationCompletionStatusKPI128;
	private List<String> jiraIterationCompletionStatusKPI134;
	private List<String> jiraIterationCompletionStatusKPI133;
	private List<String> jiraIterationCompletionStatusKPI119;
	private List<String> jiraIterationCompletionStatusKPI131;
	private List<String> jiraIterationCompletionStatusKPI138;

	private List<String> jiraIterationCompletionTypeCustomField;
	private List<String> jiraIterationIssuetypeKPI122;
	private List<String> jiraIterationIssuetypeKPI138;
	private List<String> jiraIterationIssuetypeKPI131;
	private List<String> jiraIterationIssuetypeKPI128;
	private List<String> jiraIterationIssuetypeKPI134;
	private List<String> jiraIterationIssuetypeKPI145;
	private List<String> jiraIterationIssuetypeKpi72;
	private List<String> jiraIterationIssuetypeKPI119;
	private List<String> jiraIterationIssuetypeKpi5;
	private List<String> jiraIterationIssuetypeKPI75;
	private List<String> jiraIterationIssuetypeKPI123;
	private List<String> jiraIterationIssuetypeKPI125;
	private List<String> jiraIterationIssuetypeKPI120;
	private List<String> jiraIterationIssuetypeKPI124;
	private List<String> jiraIterationIssuetypeKPI39;
	private String jiraDefectRejectionStatusKPI155;
	private List<String> jiraDodKPI155;
	private String jiraLiveStatusKPI155;

	private boolean uploadData;
	private boolean uploadDataKPI42;
	private boolean uploadDataKPI16;
	@Builder.Default
	private String notificationEnabler="On";

	private List<String> jiraIssueEpicTypeKPI153;

	private List<String> jiraDodKPI156;


	private List<String> jiraIssueTypeKPI156;

	@Builder.Default
	private Boolean leadTimeConfigRepoTool = Boolean.FALSE;

	@Builder.Default
	private String toBranchForMRKPI156 = "master";

	/**
	 * Get jira issue type names string [ ].
	 *
	 * @return the string [ ]
	 */
	public String[] getJiraIssueTypeNames() {
		return jiraIssueTypeNames == null ? null : jiraIssueTypeNames.clone();
	}

	/**
	 * Sets jira issue type names.
	 *
	 * @param jiraIssueTypeNames
	 *            the jira issue type names
	 */
	public void setJiraIssueTypeNames(String[] jiraIssueTypeNames) {
		this.jiraIssueTypeNames = jiraIssueTypeNames == null ? null : jiraIssueTypeNames.clone();
	}

	/**
	 * Get link defect to story field string [ ].
	 *
	 * @return the string [ ]
	 */
	public String[] getLinkDefectToStoryField() {
		return linkDefectToStoryField == null ? linkDefectToStoryField : linkDefectToStoryField.clone();
	}

	/**
	 * Sets link defect to story field.
	 *
	 * @param linkDefectToStoryField
	 *            the link defect to story field
	 */
	public void setLinkDefectToStoryField(String[] linkDefectToStoryField) {
		this.linkDefectToStoryField = linkDefectToStoryField == null ? null : linkDefectToStoryField.clone();
	}

}