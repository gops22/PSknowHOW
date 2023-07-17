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

package com.publicissapient.kpidashboard.common.model.application;//NOPMD

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.publicissapient.kpidashboard.common.constant.CommonConstant;
import com.publicissapient.kpidashboard.common.model.generic.BasicModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Field mapping. Represents Jira field mapping values
 */
@SuppressWarnings("PMD.TooManyFields")
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "field_mapping")
public class FieldMapping extends BasicModel {

	private ObjectId projectToolConfigId;
	private ObjectId basicProjectConfigId;
	private String projectId;
	private String sprintName;
	private String epicName;
	private List<String> jiradefecttype;//TODO: duplicate

	// defectPriority
	private List<String> defectPriority;//TODO: Extra field
	private List<String> defectPriorityKPI135;
	private List<String> defectPriorityKPI14;
	private List<String> defectPriorityQAKPI111;
	private List<String> defectPriorityKPI82;
	private List<String> defectPriorityKPI133;

	private String[] jiraIssueTypeNames;//TODO: duplicate
	private String[] jiraIssueTypeNamesAVR;
	private List<String> jiraIssueEpicType;
	private String storyFirstStatus;//TODO: duplicate
	private String storyFirstStatusKPI3;
	private String rootCause;
	private List<String> jiraStatusForDevelopment;// TODO: Extra field
	private List<String> jiraStatusForDevelopmentAVR;
	private List<String> jiraStatusForDevelopmentKPI82;
	private List<String> jiraStatusForDevelopmentKPI135;
	@Builder.Default
	private List<String> jiraStatusForQa = Arrays.asList("Ready For Testing", "In Testing");
	@Builder.Default
	private List<String> jiraStatusForQaKPI135 = Arrays.asList("Ready For Testing", "In Testing");
	@Builder.Default
	private List<String> jiraStatusForQaKPI82 = Arrays.asList("Ready For Testing", "In Testing");
	// type of test cases
	private List<String> jiraDefectInjectionIssueType;//TODO: Extra field
	private List<String> jiraDefectInjectionIssueTypeKPI14;
	private List<String> jiraDod;//TODO: Extra field
	private List<String> jiraDodKPI14;
	private List<String> jiraDodQAKPI111;
	private List<String> jiraDodKPI3;
	private List<String> jiraDodPDA;

	private String jiraDefectCreatedStatus;//TODO: Extra field
	private String jiraDefectCreatedStatusKPI14;
	private List<String> jiraTechDebtIssueType;
	private String jiraTechDebtIdentification;
	private String jiraTechDebtCustomField;
	private List<String> jiraTechDebtValue;
	private String jiraDefectRejectionStatus;// TODO: Extra field //test done
	private String jiraDefectRejectionStatusAVR;
	private String jiraDefectRejectionStatusKPI28;
	private String jiraDefectRejectionStatusKPI34;
	private String jiraDefectRejectionStatusKPI37;
	private String jiraDefectRejectionStatusKPI35;
	private String jiraDefectRejectionStatusKPI82;
	private String jiraDefectRejectionStatusKPI135;
	private String jiraDefectRejectionStatusKPI133;
	private String jiraDefectRejectionStatusRCAKPI36;
	private String jiraDefectRejectionStatusKPI14;
	private String jiraDefectRejectionStatusQAKPI111;
	private String jiraBugRaisedByIdentification;//TODO: duplicate
	private List<String> jiraBugRaisedByValue;//TODO: duplicate

	private List<String> jiraDefectSeepageIssueType;// TODO: Extra field
	private List<String> jiraIssueTypeKPI35;
	private String jiraBugRaisedByCustomField;
	private List<String> jiraDefectRemovalStatus;// TODO: Extra field
	private List<String> jiraDefectRemovalStatusKPI34;
	private List<String> jiraDefectRemovalIssueType;// TODO: Extra field
	private List<String> jiraDefectRemovalIssueTypeKPI34;
	// Added for Defect Reopen Rate KPI.
	private List<String> jiraDefectClosedStatus;

	private String jiraStoryPointsCustomField;
	// parent issue type for the test
	private List<String> jiraTestAutomationIssueType;
	// value of the automated test case Eg. Yes, Cannot Automate, No

	private List<String> jiraSprintVelocityIssueType;// TODO: Extra field  //test done
	private List<String> jiraSprintVelocityIssueTypeBR;

	private List<String> jiraSprintCapacityIssueType;// TODO: Extra field
	private List<String> jiraSprintCapacityIssueTypeKpi46;

	private List<String> jiraDefectRejectionlIssueType;// TODO: Extra field
	private List<String> jiraIssueTypeKPI37;

	private List<String> jiraDefectCountlIssueType;// TODO: Extra field // test done
	private List<String> jiraDefectCountlIssueTypeKPI28;
	private List<String> jiraDefectCountlIssueTypeKPI36;

	private List<String> jiraIssueDeliverdStatus;// TODO: Extra field //test done
	private List<String> jiraIssueDeliverdStatusBR;
	private List<String> jiraIssueDeliverdStatusAVR;
	private List<String> jiraIssueDeliverdStatusKPI126;
	private List<String> jiraIssueDeliverdStatusKPI82;
	private String readyForDevelopmentStatus;

	private String jiraDor;// TODO: Extra field
	private String jiraDorKPI3;

	private List<String> jiraIntakeToDorIssueType;// TODO: Extra field
	private List<String> jiraIssueTypeKPI3;

	private List<String> jiraStoryIdentification;// TODO: Duplicate
	private List<String> jiraStoryIdentificationKpi40;

	private String jiraLiveStatus;//TODO: Extra field
	private String jiraLiveStatusKPI3;
	private String jiraLiveStatusLTK;
	private String jiraLiveStatusNOPK;
	private String jiraLiveStatusNOSK;
	private String jiraLiveStatusNORK;
	private String jiraLiveStatusOTA;//openticketaging
	private String jiraLiveStatusPDA;//productionissueaging   //done till here

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
	private List<String> excludeStatusKpi129;

	private String jiraStatusMappingCustomField;

	private List<String> rootCauseValue;
	private List<String> excludeRCAFromFTPR;//TODO: Extra field // test done
	private List<String> excludeRCAFromKPI82;
	private List<String> excludeRCAFromKPI135;
	private List<String> excludeRCAFromKPI14;
	private List<String> excludeRCAFromQAKPI111;
	private List<String> excludeRCAFromKPI133;

	// For Lloyds KPIs
	private List<String> jiraDorToLiveIssueType;
	private List<String> jiraProductiveStatus;

	private List<String> jiraCommitmentReliabilityIssueType;

	private List<String> resolutionTypeForRejection;// TODO: Extra field  //test done
	private List<String> resolutionTypeForRejectionAVR;
	private List<String> resolutionTypeForRejectionKPI28;
	private List<String> resolutionTypeForRejectionKPI34;
	private List<String> resolutionTypeForRejectionKPI37;
	private List<String> resolutionTypeForRejectionKPI35;
	private List<String> resolutionTypeForRejectionKPI82;
	private List<String> resolutionTypeForRejectionKPI135;
	private List<String> resolutionTypeForRejectionKPI133;
	private List<String> resolutionTypeForRejectionRCAKPI36;
	private List<String> resolutionTypeForRejectionKPI14;
	private List<String> resolutionTypeForRejectionQAKPI111;

	private List<String> jiraQADefectDensityIssueType;// TODO: Extra field
	private List<String> jiraQAKPI111IssueType;

	private String jiraBugRaisedByQACustomField;
	private String jiraBugRaisedByQAIdentification;
	private List<String> jiraBugRaisedByQAValue;
	private List<String> jiraDefectDroppedStatus;

	// Epic custom Field mapping
	private String epicCostOfDelay;
	private String epicRiskReduction;
	private String epicUserBusinessValue;
	private String epicWsjf;
	private String epicTimeCriticality;
	private String epicJobSize;

	// Squad Mapping
	private String squadIdentifier;
	private List<String> squadIdentMultiValue;
	private String squadIdentSingleValue;

	// Production Defect Mapping
	private String productionDefectCustomField;
	private String productionDefectIdentifier;
	private List<String> productionDefectValue;
	private String productionDefectComponentValue;

	// field for In Progress status
	private List<String> jiraStatusForInProgress;// TODO: Extra field
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
	private List<String> issueStatusExcluMissingWork;// TODO: Extra field
	private List<String> issueStatusExcluMissingWorkKPI124;

	// issue On Hold status to exclude Closure possible
	private List<String> jiraOnHoldStatus;

	// field for FTPR
	private List<String> jiraFTPRStoryIdentification;// TODO: Extra field
	private List<String> jiraKPI82StoryIdentification;
	private List<String> jiraKPI135StoryIdentification;

	// field for Wasting - wait status
	private List<String> jiraWaitStatus;// TODO: Extra field
	private List<String> jiraWaitStatusKPI131;

	// field for Wasting - block status
	private List<String> jiraBlockedStatus;// TODO: Extra field
	private List<String> jiraBlockedStatusKPI131;

	// field for Wasting - Include Blocked Status
	private String jiraIncludeBlockedStatus; // TODO: Extra field
	private String jiraIncludeBlockedStatusKPI131;

	// for for JiraDueDate
	@Builder.Default
	private String jiraDueDateField = CommonConstant.DUE_DATE;
	private String jiraDueDateCustomField;
	private String jiraDevDueDateCustomField;
	private List<String> jiraDevDoneStatus;// TODO: Extra field
	private List<String> jiraDevDoneStatusKPI119;
	private List<String> jiraDevDoneStatusKPI145;
	private List<String> jiraDevDoneStatusKPI128;

	// For DTS_21154 - field for Team refinement status
	private List<String> jiraRejectedInRefinement;

	// For DTS_21154 - field for Stakeholder refinement status
	private List<String> jiraAcceptedInRefinement;

	// For DTS_21154 - field for Stakeholder refinement status
	private List<String> jiraReadyForRefinement;
	private List<String> jiraFtprRejectStatus;

	private List<String> jiraIterationCompletionStatusCustomField;// TODO: Duplicate //test done
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
	private List<String> jiraIterationCompletionStatusBRE;

	private List<String> jiraIterationCompletionTypeCustomField;// TODO: Extra field  //test done
	private List<String> jiraIterationIssuetypeKPI122;
	private List<String> jiraIterationIssuetypeBRE;
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
	private LocalDate createdDate;

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

}