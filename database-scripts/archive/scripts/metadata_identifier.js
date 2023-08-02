db.getCollection('metadata_identifier').remove({});
db.metadata_identifier.insert([{
        "tool": "Jira",
        "templateName": "DOJO Agile Template",
        "templateCode": "1",
        "isKanban": true,
        "disabled": false,
        "issues":
        [{
                "type": "issuetype",
                "value": [
                    "Change Request",
                    "Enabler Story",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "ticketCountIssueType",
                "value": [
                    "Change Request",
                    "Enabler Story",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "ticketVelocityStatusIssue",
                "value": [
                    "Change Request",
                    "Enabler Story",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "epic",
                "value": [
                    "Epic"
                ]
            },
            {
                "type": "kanbanCycleTimeIssue",
                "value": [
                    "Change Request",
                    "Enabler Story",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "kanbanTechDebtIssueType",
                "value": [
                    "Story",
                    "Enabler Story"
                ]
            }
        ],
        "customfield": [{
                "type": "storypoint",
                "value": [
                    "Story Points"
                ]
            },
            {
                "type": "rootcause",
                "value": [
                    "Root Cause"
                ]
            },
            {
                "type": "techdebt",
                "value": [
                    "Tech Debt"
                ]
            },
            {
                "type": "uat",
                "value": [
                    "UAT"
                ]
            },
            {
                "type": "timeCriticality",
                "value": [
                    "Time Criticality"
                ]
            },
            {
                "type": "wsjf",
                "value": [
                    "WSJF"
                ]
            },
            {
                "type": "costOfDelay",
                "value": [
                    "Cost of Delay"
                ]
            },
            {
                "type": "businessValue",
                "value": [
                    "User-Business Value"
                ]
            },
            {
                "type": "riskReduction",
                "value": [
                    "Risk Reduction-Opportunity Enablement Value"
                ]
            },
            {
                "type": "jobSize",
                "value": [
                    "Job Size"
                ]
            }
        ],
        "workflow": [{
                "type": "firststatus",
                "value": [
                    "Open"
                ]
            },
            {
                "type": "delivered",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketLiveStatus",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketResolvedStatus",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketClosedStatus",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketWIPStatus",
                "value": [
                    "IN ANALYSIS",
                    "In Development",
                    "Ready for Testing",
                    "In Testing"
                ]
            },
            {
                "type": "ticketRejectedStatus",
                "value": [
                    "Dropped",
                    "Rejected"
                ]
            },
            {
                "type": "ticketTriagedStatus",
                "value": [
                    "OPEN"
                ]
            }
        ]
    },
    {
        "tool": "Jira",
        "templateName": "DOJO Safe Template",
        "templateCode": "2",
        "isKanban": true,
        "disabled": false,
        "issues": [{
                "type": "issuetype",
                "value": [
                    "Change Request",
                    "Enabler Story",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "ticketCountIssueType",
                "value": [
                    "Change Request",
                    "Enabler Story",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "ticketVelocityStatusIssue",
                "value": [
                    "Change Request",
                    "Enabler Story",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "epic",
                "value": [
                    "Epic"
                ]
            },
            {
                "type": "kanbanCycleTimeIssue",
                "value": [
                    "Change Request",
                    "Enabler Story",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "kanbanTechDebtIssueType",
                "value": [
                    "Story",
                    "Enabler Story"
                ]
            }
        ],
        "customfield": [{
                "type": "storypoint",
                "value": [
                    "Story Points"
                ]
            },
            {
                "type": "rootcause",
                "value": [
                    "Root Cause"
                ]
            },
            {
                "type": "techdebt",
                "value": [
                    "Tech Debt"
                ]
            },
            {
                "type": "uat",
                "value": [
                    "UAT"
                ]
            },
            {
                "type": "timeCriticality",
                "value": [
                    "Time Criticality"
                ]
            },
            {
                "type": "wsjf",
                "value": [
                    "WSJF"
                ]
            },
            {
                "type": "costOfDelay",
                "value": [
                    "Cost of Delay"
                ]
            },
            {
                "type": "businessValue",
                "value": [
                    "User-Business Value"
                ]
            },
            {
                "type": "riskReduction",
                "value": [
                    "Risk Reduction-Opportunity Enablement Value"
                ]
            },
            {
                "type": "jobSize",
                "value": [
                    "Job Size"
                ]
            }
        ],
        "workflow": [{
                "type": "firststatus",
                "value": [
                    "Open"
                ]
            },
            {
                "type": "delivered",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketLiveStatus",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketResolvedStatus",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketClosedStatus",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketWIPStatus",
                "value": [
                    "IN ANALYSIS",
                    "In Development",
                    "Ready for Testing",
                    "In Testing"
                ]
            },
            {
                "type": "ticketRejectedStatus",
                "value": [
                    "Dropped",
                    "Rejected"
                ]
            },
            {
                "type": "ticketTriagedStatus",
                "value": [
                    "OPEN"
                ]
            }
        ]
    },
    {
        "tool": "Jira",
        "templateName": "DOJO Studio Template",
        "templateCode": "3",
        "isKanban": true,
        "disabled": false,
        "issues": [{
                "type": "issuetype",
                "value": [
                    "Change Request",
                    "Enabler Story",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "ticketCountIssueType",
                "value": [
                    "Change Request",
                    "Enabler Story",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "ticketVelocityStatusIssue",
                "value": [
                    "Change Request",
                    "Enabler Story",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "epic",
                "value": [
                    "Epic"
                ]
            },
            {
                "type": "kanbanCycleTimeIssue",
                "value": [
                    "Change Request",
                    "Enabler Story",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "kanbanTechDebtIssueType",
                "value": [
                    "Story",
                    "Enabler Story"
                ]
            }
        ],
        "customfield": [{
                "type": "storypoint",
                "value": [
                    "Story Points"
                ]
            },
            {
                "type": "rootcause",
                "value": [
                    "Root Cause"
                ]
            },
            {
                "type": "techdebt",
                "value": [
                    "Tech Debt"
                ]
            },
            {
                "type": "uat",
                "value": [
                    "UAT"
                ]
            },
            {
                "type": "timeCriticality",
                "value": [
                    "Time Criticality"
                ]
            },
            {
                "type": "wsjf",
                "value": [
                    "WSJF"
                ]
            },
            {
                "type": "costOfDelay",
                "value": [
                    "Cost of Delay"
                ]
            },
            {
                "type": "businessValue",
                "value": [
                    "User-Business Value"
                ]
            },
            {
                "type": "riskReduction",
                "value": [
                    "Risk Reduction-Opportunity Enablement Value"
                ]
            },
            {
                "type": "jobSize",
                "value": [
                    "Job Size"
                ]
            }
        ],
        "workflow": [{
                "type": "firststatus",
                "value": [
                    "Open"
                ]
            },
            {
                "type": "delivered",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketLiveStatus",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketResolvedStatus",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketClosedStatus",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketWIPStatus",
                "value": [
                    "IN ANALYSIS",
                    "In Development",
                    "Ready for Testing",
                    "In Testing"
                ]
            },
            {
                "type": "ticketRejectedStatus",
                "value": [
                    "Dropped",
                    "Rejected"
                ]
            },
            {
                "type": "ticketTriagedStatus",
                "value": [
                    "OPEN"
                ]
            }
        ]
    },
    {
        "tool": "Jira",
        "templateName": "DOJO Agile Template",
        "templateCode": "4",
        "isKanban": false,
        "disabled": false,
        "issues": [{
                        "type": "jiradefecttype",
                        "value": [
                          "Defect"
                        ]
                    },
                    {
                        "type": "jiraIssueTypeNames",
                        "value": [
                                                "Story",
                                                "Enabler Story",
                                                "Change request",
                                                "Defect"
                        ]
                    },
                    {
                        "type": "jiraIssueEpicType",
                        "value": [
"Epic"
                        ]
                    },
                    {
                        "type": "jiraDefectInjectionIssueTypeKPI14",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Change request",
                                                "Defect"
                        ]
                    },
                    {
                        "type": "jiraIssueTypeKPI35",
                        "value": [
"Story",
                                                "Enabler Story"
                        ]
                    },
                    {
                        "type": "jiraDefectRemovalIssueTypeKPI34",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Change request",
                                                "Defect"
                        ]
                    },
                    {
                        "type": "jiraTestAutomationIssueType",
                        "value": [
                                                "Story",
                                                "Enabler Story",
                                                "Change request",
                                                "Defect"

                        ]
                    },
                    {
                        "type": "jiraSprintVelocityIssueTypeKPI138",
                        "value": [
                                                "Story",
                                                "Enabler Story",
                                                "Change request",
                                                "Defect"

                        ]
                    },
                    {
                        "type": "jiraSprintCapacityIssueTypeKpi46",
                        "value": [
                         "Story",
                                                                        "Enabler Story",
                                                                        "Change request",
                                                                        "Defect"
                        ]
                    },
                    {
                        "type": "jiraIssueTypeKPI37",
                        "value": [
                                                "Story",
                                                "Enabler Story",
                                                "Change request",
                                                "Defect"

                        ]
                    },
                    {
                        "type": "jiraDefectCountlIssueTypeKPI28",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Change request",
                                                "Defect"
                        ]
                    },
                    {
                        "type": "jiraDefectCountlIssueTypeKPI36",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Change request",
                                                "Defect"
                        ]
                    },
                    {
                        "type": "jiraIssueTypeKPI3",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Change request",
                                                "Defect"
                        ]
                    },
                    {
                        "type": "jiraQAKPI111IssueType",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Change request",
                                                "Defect"
                        ]
                    },
                    {
                        "type": "jiraStoryIdentificationKPI129",
                        "value": [
"Story",
                                                "Enabler Story"
                        ]
                    },
                    {
                        "type": "jiraStoryIdentificationKpi40",
                        "value": [
"Story",
                                                "Enabler Story"
                        ]
                    },
                    {
                        "type": "jiraTechDebtIssueType",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Change request",
                                                "Defect"
                        ]
                    }
                ],
        "customfield": [

                    {
                        "type": "jiraStoryPointsCustomField",
                        "value": [
                        "Story Points"
                        ]
                    },
                    {
                        "type": "epicCostOfDelay",
                        "value": [
                        "Story Points"
                        ]
                    },
                    {
                        "type": "epicRiskReduction",
                        "value": [
                        "Risk Reduction-Opportunity Enablement Value"
                        ]
                    },
                    {
                        "type": "epicUserBusinessValue",
                        "value": [
                        "User-Business Value"
                        ]
                    },
                    {
                        "type": "epicWsjf",
                        "value": [
                        "WSJF"
                        ]
                    },
                    {
                        "type": "epicTimeCriticality",
                        "value": [
                        "Time Criticality"
                        ]
                    },
                    {
                        "type": "epicJobSize",
                        "value": [
"Job Size"
                        ]
                    },
                    {
                        "type": "rootCause",
                        "value": [
"Root Cause"
                        ]
                    },
                    {
                         "type": "sprintName",
                         "value": [
"Sprint"
                         ]
                    }
                ],
        "workflow": [

                    {
                        "type": "storyFirstStatusKPI148",
                        "value": [
"Open"
                        ]
                    }, {
                        "type": "storyFirstStatusKPI3",
                        "value": [
"Open"
                        ]
                    },
                    {
                        "type": "jiraStatusForQaKPI148",
                        "value": [
"In Testing"
                        ]
                    },
                    {
                        "type": "jiraStatusForQaKPI135",
                        "value": [
"In Testing"
                        ]
                    },
                    {
                        "type": "jiraStatusForQaKPI82",
                        "value": [
"In Testing"
                        ]
                    },
                    {
                        "type": "jiraStatusForDevelopmentKPI82",
                        "value": [
"Closed"
                        ]
                    },
                    {
                        "type": "jiraStatusForDevelopmentKPI135",
                        "value": [
"Closed"
                        ]
                    }, ,
                    {
                        "type": "jiraDefectCreatedStatusKPI14",
                        "value": [
"Open"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI152",
                        "value": [
"Rejected"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI151",
                        "value": [
"Rejected"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI28",
                        "value": [
"Rejected"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI34",
                        "value": [
"Rejected"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI37",
                        "value": [
"Rejected"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI35",
                        "value": [
"Rejected"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI82",
                        "value": [
"Rejected"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI135",
                        "value": [
"Rejected"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI133",
                        "value": [
"Rejected"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusRCAKPI36",
                        "value": [
"Rejected"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI14",
                        "value": [
"Rejected"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusQAKPI111",
                        "value": [
"Rejected"
                        ]
                    } {
                        "type": "jiraDefectRemovalStatusKPI34",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectClosedStatusKPI137",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraIssueDeliverdStatusKPI138",
                        "value": [
"Closed"
                        ]
                    },
                    {
                        "type": "jiraIssueDeliverdStatusKPI126",
                        "value": [
"Closed"
                        ]
                    },
                    {
                        "type": "jiraIssueDeliverdStatusKPI82",
                        "value": [
"Closed"
                        ]
                    },
                    {
                        "type": "jiraDorKPI3",
                        "value": [
"Open"
                        ]
                    },
                    {
                        "type": "jiraLiveStatusKPI3",
                        "value": [
"Closed"
                        ]
                    },
                    {
                        "type": "jiraLiveStatusKPI127",
                        "value": [
"Closed"
                        ]
                    },
                    {
                        "type": "jiraLiveStatusKPI152",
                        "value": [
"Closed"
                        ]
                    },
                    {
                        "type": "jiraLiveStatusKPI151",
                        "value": [
"Closed"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI28",
                        "value": [
"Dropped",
                                                "Rejected"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI34",
                        "value": [
"Dropped",
                                                "Rejected"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI37",
                        "value": [
"Dropped",
                                                "Rejected"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI35",
                        "value": [
"Dropped",
                                                "Rejected"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI135",
                        "value": [
"Dropped",
                                                "Rejected"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI82",
                        "value": [
"Dropped",
                                                "Rejected"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI133",
                        "value": [
"Dropped",
                                                "Rejected"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionRCAKPI36",
                        "value": [
"Dropped",
                                                "Rejected"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionQAKPI111",
                        "value": [
"Dropped",
                                                "Rejected"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI14",
                        "value": [
"Dropped",
                                                "Rejected"
                        ]
                    },
                    {
                        "type": "issueStatusExcluMissingWorkKPI124",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectDroppedStatusKPI127",
                        "value": [
"Dropped",
                                                "Rejected"
                        ]
                    },
                    {
                        "type": "jiraBlockedStatusKPI131",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraWaitStatusKPI131",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI148",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI122",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI145",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI125",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI128",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI123",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI119",
                        "value": [

                        ]
                    },
                                         {
                                             "type": "jiraDodKPI14",
                                             "value": [
                      "Closed"
                                             ]
                                         },
                                         {
                                             "type": "jiraDodQAKPI111",
                                             "value": [
                      "Closed"
                                             ]
                                         },
                                         {
                                             "type": "jiraDodKPI3",
                                             "value": [
                      "Closed"
                                             ]
                                         },
                                         {
                                             "type": "jiraDodKPI127",
                                             "value": [
                      "Closed"
                                             ]
                                         },
                                         {
                                             "type": "jiraDodKPI152",
                                             "value": [
                      "Closed"
                                             ]
                                         },
                                         {
                                             "type": "jiraDodKPI151",
                                             "value": [
                      "Closed"
                                             ]
                                         }
                ]
    },
    {
        "tool": "Jira",
        "templateName": "DOJO Safe Template",
        "templateCode": "5",
        "isKanban": false,
        "disabled": false,
        "issues": [{
                                "type": "jiradefecttype",
                                "value": [
                                  "Defect"
                                ]
                            },
                            {
                                "type": "jiraIssueTypeNames",
                                "value": [
                                                        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraIssueEpicType",
                                "value": [
        "Epic"
                                ]
                            },
                            {
                                "type": "jiraDefectInjectionIssueTypeKPI14",
                                "value": [
        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraIssueTypeKPI35",
                                "value": [
        "Story",
                                                        "Enabler Story"
                                ]
                            },
                            {
                                "type": "jiraDefectRemovalIssueTypeKPI34",
                                "value": [
        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraTestAutomationIssueType",
                                "value": [
                                                        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"

                                ]
                            },
                            {
                                "type": "jiraSprintVelocityIssueTypeKPI138",
                                "value": [
                                                        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"

                                ]
                            },
                            {
                                "type": "jiraSprintCapacityIssueTypeKpi46",
                                "value": [
                                 "Story",
                                                                                "Enabler Story",
                                                                                "Change request",
                                                                                "Defect"
                                ]
                            },
                            {
                                "type": "jiraIssueTypeKPI37",
                                "value": [
                                                        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"

                                ]
                            },
                            {
                                "type": "jiraDefectCountlIssueTypeKPI28",
                                "value": [
        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraDefectCountlIssueTypeKPI36",
                                "value": [
        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraIssueTypeKPI3",
                                "value": [
        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraQAKPI111IssueType",
                                "value": [
        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraStoryIdentificationKPI129",
                                "value": [
        "Story",
                                                        "Enabler Story"
                                ]
                            },
                            {
                                "type": "jiraStoryIdentificationKpi40",
                                "value": [
        "Story",
                                                        "Enabler Story"
                                ]
                            },
                            {
                                "type": "jiraTechDebtIssueType",
                                "value": [

                                ]
                            }
                        ],
                "customfield": [

                            {
                                "type": "jiraStoryPointsCustomField",
                                "value": [
                                "Story Points"
                                ]
                            },
                            {
                                "type": "epicCostOfDelay",
                                "value": [
                                "Story Points"
                                ]
                            },
                            {
                                "type": "epicRiskReduction",
                                "value": [
                                "Risk Reduction-Opportunity Enablement Value"
                                ]
                            },
                            {
                                "type": "epicUserBusinessValue",
                                "value": [
                                "User-Business Value"
                                ]
                            },
                            {
                                "type": "epicWsjf",
                                "value": [
                                "WSJF"
                                ]
                            },
                            {
                                "type": "epicTimeCriticality",
                                "value": [
                                "Time Criticality"
                                ]
                            },
                            {
                                "type": "epicJobSize",
                                "value": [
        "Job Size"
                                ]
                            },
                            {
                                "type": "rootCause",
                                "value": [
        "Root Cause"
                                ]
                            },
                            {
                                 "type": "sprintName",
                                 "value": [
        "Sprint"
                                 ]
                            }
                        ],
                "workflow": [

                            {
                                "type": "storyFirstStatusKPI148",
                                "value": [
        "Open"
                                ]
                            }, {
                                "type": "storyFirstStatusKPI3",
                                "value": [
        "Open"
                                ]
                            },
                            {
                                "type": "jiraStatusForQaKPI148",
                                "value": [
        "In Testing"
                                ]
                            },
                            {
                                "type": "jiraStatusForQaKPI135",
                                "value": [
        "In Testing"
                                ]
                            },
                            {
                                "type": "jiraStatusForQaKPI82",
                                "value": [
        "In Testing"
                                ]
                            },
                            {
                                "type": "jiraStatusForDevelopmentKPI82",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraStatusForDevelopmentKPI135",
                                "value": [
        "Closed"
                                ]
                            }, ,
                            {
                                "type": "jiraDefectCreatedStatusKPI14",
                                "value": [
        "Open"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI152",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI151",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI28",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI34",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI37",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI35",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI82",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI135",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI133",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusRCAKPI36",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI14",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusQAKPI111",
                                "value": [
        "Rejected"
                                ]
                            } {
                                "type": "jiraDefectRemovalStatusKPI34",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraDefectClosedStatusKPI137",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraIssueDeliverdStatusKPI138",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraIssueDeliverdStatusKPI126",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraIssueDeliverdStatusKPI82",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraDorKPI3",
                                "value": [
        "Open"
                                ]
                            },
                            {
                                "type": "jiraLiveStatusKPI3",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraLiveStatusKPI127",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraLiveStatusKPI152",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraLiveStatusKPI151",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI28",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI34",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI37",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI35",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI135",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI82",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI133",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionRCAKPI36",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionQAKPI111",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI14",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "issueStatusExcluMissingWorkKPI124",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraDefectDroppedStatusKPI127",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraBlockedStatusKPI131",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraWaitStatusKPI131",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI148",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI122",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI145",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI125",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI128",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI123",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI119",
                                "value": [

                                ]
                            },
                                                 {
                                                     "type": "jiraDodKPI14",
                                                     "value": [
                              "Closed"
                                                     ]
                                                 },
                                                 {
                                                     "type": "jiraDodQAKPI111",
                                                     "value": [
                              "Closed"
                                                     ]
                                                 },
                                                 {
                                                     "type": "jiraDodKPI3",
                                                     "value": [
                              "Closed"
                                                     ]
                                                 },
                                                 {
                                                     "type": "jiraDodKPI127",
                                                     "value": [
                              "Closed"
                                                     ]
                                                 },
                                                 {
                                                     "type": "jiraDodKPI152",
                                                     "value": [
                              "Closed"
                                                     ]
                                                 },
                                                 {
                                                     "type": "jiraDodKPI151",
                                                     "value": [
                              "Closed"
                                                     ]
                                                 }
                        ]
    },
    {
        "tool": "Jira",
        "templateName": "DOJO Studio Template",
        "templateCode": "6",
        "isKanban": false,
        "disabled": false,
        "issues": [{
                                "type": "jiradefecttype",
                                "value": [
                                  "Defect"
                                ]
                            },
                            {
                                "type": "jiraIssueTypeNames",
                                "value": [
                                                        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraIssueEpicType",
                                "value": [
        "Epic"
                                ]
                            },
                            {
                                "type": "jiraDefectInjectionIssueTypeKPI14",
                                "value": [
        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraIssueTypeKPI35",
                                "value": [
        "Story",
                                                        "Enabler Story"
                                ]
                            },
                            {
                                "type": "jiraDefectRemovalIssueTypeKPI34",
                                "value": [
        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraTestAutomationIssueType",
                                "value": [
                                                        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"

                                ]
                            },
                            {
                                "type": "jiraSprintVelocityIssueTypeKPI138",
                                "value": [
                                                        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"

                                ]
                            },
                            {
                                "type": "jiraSprintCapacityIssueTypeKpi46",
                                "value": [
                                 "Story",
                                                                                "Enabler Story",
                                                                                "Change request",
                                                                                "Defect"
                                ]
                            },
                            {
                                "type": "jiraIssueTypeKPI37",
                                "value": [
                                                        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"

                                ]
                            },
                            {
                                "type": "jiraDefectCountlIssueTypeKPI28",
                                "value": [
        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraDefectCountlIssueTypeKPI36",
                                "value": [
        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraIssueTypeKPI3",
                                "value": [
        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraQAKPI111IssueType",
                                "value": [
        "Story",
                                                        "Enabler Story",
                                                        "Change request",
                                                        "Defect"
                                ]
                            },
                            {
                                "type": "jiraStoryIdentificationKPI129",
                                "value": [
        "Story",
                                                        "Enabler Story"
                                ]
                            },
                            {
                                "type": "jiraStoryIdentificationKpi40",
                                "value": [
        "Story",
                                                        "Enabler Story"
                                ]
                            },
                            {
                                "type": "jiraTechDebtIssueType",
                                "value": [

                                ]
                            }
                        ],
                "customfield": [

                            {
                                "type": "jiraStoryPointsCustomField",
                                "value": [
                                "Story Points"
                                ]
                            },
                            {
                                "type": "epicCostOfDelay",
                                "value": [
                                "Story Points"
                                ]
                            },
                            {
                                "type": "epicRiskReduction",
                                "value": [
                                "Risk Reduction-Opportunity Enablement Value"
                                ]
                            },
                            {
                                "type": "epicUserBusinessValue",
                                "value": [
                                "User-Business Value"
                                ]
                            },
                            {
                                "type": "epicWsjf",
                                "value": [
                                "WSJF"
                                ]
                            },
                            {
                                "type": "epicTimeCriticality",
                                "value": [
                                "Time Criticality"
                                ]
                            },
                            {
                                "type": "epicJobSize",
                                "value": [
        "Job Size"
                                ]
                            },
                            {
                                "type": "rootCause",
                                "value": [
        "Root Cause"
                                ]
                            },
                            {
                                 "type": "sprintName",
                                 "value": [
        "Sprint"
                                 ]
                            }
                        ],
                "workflow": [

                            {
                                "type": "storyFirstStatusKPI148",
                                "value": [
        "Open"
                                ]
                            }, {
                                "type": "storyFirstStatusKPI3",
                                "value": [
        "Open"
                                ]
                            },
                            {
                                "type": "jiraStatusForQaKPI148",
                                "value": [
        "In Testing"
                                ]
                            },
                            {
                                "type": "jiraStatusForQaKPI135",
                                "value": [
        "In Testing"
                                ]
                            },
                            {
                                "type": "jiraStatusForQaKPI82",
                                "value": [
        "In Testing"
                                ]
                            },
                            {
                                "type": "jiraStatusForDevelopmentKPI82",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraStatusForDevelopmentKPI135",
                                "value": [
        "Closed"
                                ]
                            }, ,
                            {
                                "type": "jiraDefectCreatedStatusKPI14",
                                "value": [
        "Open"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI152",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI151",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI28",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI34",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI37",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI35",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI82",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI135",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI133",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusRCAKPI36",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusKPI14",
                                "value": [
        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraDefectRejectionStatusQAKPI111",
                                "value": [
        "Rejected"
                                ]
                            } {
                                "type": "jiraDefectRemovalStatusKPI34",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraDefectClosedStatusKPI137",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraIssueDeliverdStatusKPI138",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraIssueDeliverdStatusKPI126",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraIssueDeliverdStatusKPI82",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraDorKPI3",
                                "value": [
        "Open"
                                ]
                            },
                            {
                                "type": "jiraLiveStatusKPI3",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraLiveStatusKPI127",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraLiveStatusKPI152",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "jiraLiveStatusKPI151",
                                "value": [
        "Closed"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI28",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI34",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI37",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI35",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI135",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI82",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI133",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionRCAKPI36",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionQAKPI111",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "resolutionTypeForRejectionKPI14",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "issueStatusExcluMissingWorkKPI124",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraDefectDroppedStatusKPI127",
                                "value": [
        "Dropped",
                                                        "Rejected"
                                ]
                            },
                            {
                                "type": "jiraBlockedStatusKPI131",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraWaitStatusKPI131",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI148",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI122",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI145",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI125",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI128",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI123",
                                "value": [

                                ]
                            },
                            {
                                "type": "jiraStatusForInProgressKPI119",
                                "value": [

                                ]
                            },
                                                 {
                                                     "type": "jiraDodKPI14",
                                                     "value": [
                              "Closed"
                                                     ]
                                                 },
                                                 {
                                                     "type": "jiraDodQAKPI111",
                                                     "value": [
                              "Closed"
                                                     ]
                                                 },
                                                 {
                                                     "type": "jiraDodKPI3",
                                                     "value": [
                              "Closed"
                                                     ]
                                                 },
                                                 {
                                                     "type": "jiraDodKPI127",
                                                     "value": [
                              "Closed"
                                                     ]
                                                 },
                                                 {
                                                     "type": "jiraDodKPI152",
                                                     "value": [
                              "Closed"
                                                     ]
                                                 },
                                                 {
                                                     "type": "jiraDodKPI151",
                                                     "value": [
                              "Closed"
                                                     ]
                                                 }
                        ]
    },
    {
        "tool": "Jira",
        "templateName": "Standard Template",
        "templateCode": "7",
        "isKanban": false,
        "disabled": false,
        "issues": [{
                        "type": "jiradefecttype",
                        "value": [
 "Defect",
                                                "Bug"
                        ]
                    },
                    {
                        "type": "jiraIssueTypeNames",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request",
                                                "Defect",
                                                "Bug",
                                                "Epic"
                        ]
                    },
                    {
                        "type": "jiraIssueEpicType",
                        "value": [
"Epic"
                        ]
                    },
                    {
                        "type": "jiraDefectInjectionIssueTypeKPI14",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"
                        ]
                    },
                    {
                        "type": "jiraIssueTypeKPI35",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"
                        ]
                    },
                    {
                        "type": "jiraDefectRemovalIssueTypeKPI34",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"
                        ]
                    },
                    {
                        "type": "jiraTestAutomationIssueType",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"
                        ]
                    },
                    {
                        "type": "jiraSprintVelocityIssueTypeKPI138",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"
                        ]
                    },
                    {
                        "type": "jiraSprintCapacityIssueTypeKpi46",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"
                        ]
                    },
                    {
                        "type": "jiraIssueTypeKPI37",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"

                        ]
                    },
                    {
                        "type": "jiraDefectCountlIssueTypeKPI28",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"

                        ]
                    },
                    {
                        "type": "jiraDefectCountlIssueTypeKPI36",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"

                        ]
                    },
                    {
                        "type": "jiraIssueTypeKPI3",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"

                        ]
                    },
                    {
                        "type": "jiraQAKPI111IssueType",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"
                        ]
                    },
                    {
                        "type": "jiraStoryIdentificationKPI129",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"
                        ]
                    },
                    {
                        "type": "jiraStoryIdentificationKpi40",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"
                        ]
                    },
                    {
                        "type": "jiraTechDebtIssueType",
                        "value": [
"Story",
                                                "Enabler Story",
                                                "Tech Story",
                                                "Change request"
                        ]
                    }
                ],
        "customfield": [

                                    {
                                        "type": "jiraStoryPointsCustomField",
                                        "value": [
                                        "Story Points"
                                        ]
                                    },
                                    {
                                        "type": "epicCostOfDelay",
                                        "value": [
                                        "Story Points"
                                        ]
                                    },
                                    {
                                        "type": "epicRiskReduction",
                                        "value": [
                                        "Risk Reduction-Opportunity Enablement Value"
                                        ]
                                    },
                                    {
                                        "type": "epicUserBusinessValue",
                                        "value": [
                                        "User-Business Value"
                                        ]
                                    },
                                    {
                                        "type": "epicWsjf",
                                        "value": [
                                        "WSJF"
                                        ]
                                    },
                                    {
                                        "type": "epicTimeCriticality",
                                        "value": [
                                        "Time Criticality"
                                        ]
                                    },
                                    {
                                        "type": "epicJobSize",
                                        "value": [
                "Job Size"
                                        ]
                                    },
                                    {
                                        "type": "rootCause",
                                        "value": [
                "Root Cause"
                                        ]
                                    },
                                    {
                                         "type": "sprintName",
                                         "value": [
                "Sprint"
                                         ]
                                    }
                                ],
        "workflow": [

                    {
                        "type": "storyFirstStatusKPI148",
                        "value": [
"Open"
                        ]
                    }, {
                        "type": "storyFirstStatusKPI3",
                        "value": [
"Open"
                        ]
                    },
                    {
                        "type": "jiraStatusForQaKPI148",
                        "value": [
"In Testing"
                        ]
                    },
                    {
                        "type": "jiraStatusForQaKPI135",
                        "value": [
"In Testing"
                        ]
                    },
                    {
                        "type": "jiraStatusForQaKPI82",
                        "value": [
"In Testing"
                        ]
                    },
                    {
                        "type": "jiraStatusForDevelopmentKPI82",
                        "value": [
 "Implementing",
                                                "In Development",
                                                "In Analysis"
                        ]
                    },
                    {
                        "type": "jiraStatusForDevelopmentKPI135",
                        "value": [
 "Implementing",
                                                "In Development",
                                                "In Analysis"
                        ]
                    },
                    {
                        "type": "jiraDefectCreatedStatusKPI14",
                        "value": [
"Open"
                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI152",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI151",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI28",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI34",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI37",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI35",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI82",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI135",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI133",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusRCAKPI36",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusKPI14",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraDefectRejectionStatusQAKPI111",
                        "value": [

                        ]
                    } {
                        "type": "jiraDefectRemovalStatusKPI34",
                        "value": [
"Closed",
                                                "Resolved",
                                                "Ready for Delivery",
                                                "Ready for Release"
                        ]
                    },
                    {
                        "type": "jiraDefectClosedStatusKPI137",
                        "value": [
"Closed",
                                                "CLOSED"
                        ]
                    },
                    {
                        "type": "jiraIssueDeliverdStatusKPI138",
                        "value": [
"Closed",
                                                "Resolved",
                                                "Ready for Delivery",
                                                "Ready for Release"
                        ]
                    },
                    {
                        "type": "jiraIssueDeliverdStatusKPI126",
                        "value": [
"Closed",
                                                "Resolved",
                                                "Ready for Delivery",
                                                "Ready for Release"
                        ]
                    },
                    {
                        "type": "jiraIssueDeliverdStatusKPI82",
                        "value": [
"Closed",
                                                "Resolved",
                                                "Ready for Delivery",
                                                "Ready for Release"
                        ]
                    },
                    {
                        "type": "jiraDorKPI3",
                        "value": [
"Ready for Sprint Planning",
                                                "In Progress"
                        ]
                    },
                    {
                        "type": "jiraLiveStatusKPI3",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraLiveStatusKPI127",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraLiveStatusKPI152",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraLiveStatusKPI151",
                        "value": [

                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI28",
                        "value": [
"Invalid",
                                                "Duplicate",
                                                "Unrequired"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI34",
                        "value": [
"Invalid",
                                                "Duplicate",
                                                "Unrequired"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI37",
                        "value": [
"Invalid",
                                                "Duplicate",
                                                "Unrequired"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI35",
                        "value": [
"Invalid",
                                                "Duplicate",
                                                "Unrequired"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI135",
                        "value": [
"Invalid",
                                                "Duplicate",
                                                "Unrequired"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI82",
                        "value": [
"Invalid",
                                                "Duplicate",
                                                "Unrequired"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI133",
                        "value": [
"Invalid",
                                                "Duplicate",
                                                "Unrequired"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionRCAKPI36",
                        "value": [
"Invalid",
                                                "Duplicate",
                                                "Unrequired"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionQAKPI111",
                        "value": [
"Invalid",
                                                "Duplicate",
                                                "Unrequired"
                        ]
                    },
                    {
                        "type": "resolutionTypeForRejectionKPI14",
                        "value": [
"Invalid",
                                                "Duplicate",
                                                "Unrequired"
                        ]
                    },
                    {
                        "type": "issueStatusExcluMissingWorkKPI124",
                        "value": [
 "Open"
                        ]
                    },
                    {
                        "type": "jiraDefectDroppedStatusKPI127",
                        "value": [

                        ]
                    },
                    {
                        "type": "jiraBlockedStatusKPI131",
                        "value": [
"On Hold",
                                                "Blocked"
                        ]
                    },
                    {
                        "type": "jiraWaitStatusKPI131",
                        "value": [
"Ready for Testing"
                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI148",
                        "value": [
 "In Analysis",
                                                "In Development",
                                                "In Progress"
                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI122",
                        "value": [
 "In Analysis",
                                                "In Development",
                                                "In Progress"
                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI145",
                        "value": [
 "In Analysis",
                                                "In Development",
                                                "In Progress"
                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI125",
                        "value": [
 "In Analysis",
                                                "In Development",
                                                "In Progress"
                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI128",
                        "value": [
 "In Analysis",
                                                "In Development",
                                                "In Progress"
                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI123",
                        "value": [
 "In Analysis",
                                                "In Development",
                                                "In Progress"
                        ]
                    },
                    {
                        "type": "jiraStatusForInProgressKPI119",
                        "value": [
 "In Analysis",
                                                "In Development",
                                                "In Progress"
                        ]
                    },
                                        {
                                            "type": "jiraDodKPI14",
                                            "value": [
"Closed",
                                                "Resolved",
                                                "Ready for Delivery"
                                            ]
                                        },
                                        {
                                            "type": "jiraDodQAKPI111",
                                            "value": [
"Closed",
                                                "Resolved",
                                                "Ready for Delivery"
                                            ]
                                        },
                                        {
                                            "type": "jiraDodKPI3",
                                            "value": [
"Closed",
                                                "Resolved",
                                                "Ready for Delivery"
                                            ]
                                        },
                                        {
                                            "type": "jiraDodKPI127",
                                            "value": [
"Closed",
                                                "Resolved",
                                                "Ready for Delivery"
                                            ]
                                        },
                                        {
                                            "type": "jiraDodKPI152",
                                            "value": [
"Closed",
                                                "Resolved",
                                                "Ready for Delivery"
                                            ]
                                        },
                                        {
                                            "type": "jiraDodKPI151",
                                            "value": [
"Closed",
                                                "Resolved",
                                                "Ready for Delivery"
                                            ]
                                        }
                ]
    },
    {
        "tool": "Jira",
        "templateName": "Standard Template",
        "templateCode": "8",
        "isKanban": true,
        "disabled": false,
        "issues": [{
                "type": "epic",
                "value": [
                    "Epic"
                ]
            },
            {
                "type": "issuetype",
                "value": [
                    "Support Request",
                    "Incident",
                    "Project Request",
                    "Member Account Request",
                    "DOJO Consulting Request",
                    "Ticket",
                    "Bug",
                    "Change Request",
                    "Tech Story",
                    "Question",
                    "Request",
                    "Issue",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "uatdefect",
                "value": [
                    "UAT Defect"
                ]
            },
            {
                "type": "ticketVelocityStatusIssue",
                "value": [
                    "Support Request",
                    "Incident",
                    "Project Request",
                    "Member Account Request",
                    "DOJO Consulting Request",
                    "Ticket",
                    "Bug",
                    "Change Request",
                    "Tech Story",
                    "Question",
                    "Request",
                    "Issue",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "ticketThroughputIssue",
                "value": [
                    "Support Request",
                    "Incident",
                    "Project Request",
                    "Member Account Request",
                    "DOJO Consulting Request",
                    "Ticket",
                    "Bug",
                    "Change Request",
                    "Tech Story",
                    "Question",
                    "Request",
                    "Issue",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "ticketWipClosedIssue",
                "value": [
                    "Support Request",
                    "Incident",
                    "Project Request",
                    "Member Account Request",
                    "DOJO Consulting Request",
                    "Ticket",
                    "Bug",
                    "Change Request",
                    "Tech Story",
                    "Question",
                    "Request",
                    "Issue",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "ticketReopenIssue",
                "value": [
                    "Support Request",
                    "Incident",
                    "Project Request",
                    "Member Account Request",
                    "DOJO Consulting Request"
                ]
            },
            {
                "type": "kanbanCycleTimeIssue",
                "value": [
                    "Support Request",
                    "Incident",
                    "Project Request",
                    "Member Account Request",
                    "DOJO Consulting Request",
                    "Ticket",
                    "Bug",
                    "Change Request",
                    "Tech Story",
                    "Question",
                    "Request",
                    "Issue",
                    "Defect",
                    "Story"
                ]
            },
            {
                "type": "kanbanTechDebtIssueType",
                "value": [
                    "Support Request",
                    "Incident",
                    "Project Request",
                    "Member Account Request",
                    "DOJO Consulting Request",
                    "Ticket",
                    "Bug",
                    "Change Request",
                    "Tech Story",
                    "Question",
                    "Request",
                    "Issue",
                    "Defect",
                    "Story"
                ]
            }
        ],
        "customfield": [{
                "type": "storypoint",
                "value": [
                    "Story Points"
                ]
            },
            {
                "type": "rootcause",
                "value": [
                    "Root Cause"
                ]
            },
            {
                "type": "techdebt",
                "value": [
                    "Tech Debt"
                ]
            },
            {
                "type": "uat",
                "value": [
                    "UAT"
                ]
            },
            {
                "type": "timeCriticality",
                "value": [
                    "Time Criticality"
                ]
            },
            {
                "type": "wsjf",
                "value": [
                    "WSJF"
                ]
            },
            {
                "type": "costOfDelay",
                "value": [
                    "Cost of Delay"
                ]
            },
            {
                "type": "businessValue",
                "value": [
                    "User-Business Value"
                ]
            },
            {
                "type": "riskReduction",
                "value": [
                    "Risk Reduction-Opportunity Enablement Value"
                ]
            },
            {
                "type": "jobSize",
                "value": [
                    "Job Size"
                ]
            }
        ],
        "workflow": [{
                "type": "firststatus",
                "value": [
                    "Open"
                ]
            },
            {
                "type": "rejection",
                "value": [
                    "Rejected",
                    "Closed"
                ]
            },
            {
                "type": "delivered",
                "value": [
                    "Closed",
                    "Resolved",
                    "Ready for Delivery",
                    "Ready for Release"
                ]
            },
            {
                "type": "ticketReopenStatus",
                "value": [
                    "Reopened"
                ]
            },
            {
                "type": "ticketResolvedStatus",
                "value": [
                    "Resolved",
                    "Closed",
                    "CLOSED"
                ]
            },
            {
                "type": "ticketClosedStatus",
                "value": [
                    "Closed",
                    "CLOSED"
                ]
            },
            {
                "type": "ticketWIPStatus",
                "value": [
                    "In Progress",
                    "In Development"
                ]
            },
            {
                "type": "ticketRejectedStatus",
                "value": [
                    "Dropped",
                    "Rejected"
                ]
            },
            {
                "type": "ticketTriagedStatus",
                "value": [
                    "Assigned",
                    "REVIEWING",
                    "In Progress",
                    "OPEN",
                    "IN ANALYSIS",
                    "Approval for Implementation",
                    "ACCEPTED",
                    "In Development"
                ]
            }
        ],
        "valuestoidentify": [{
                "type": "rootCauseValue",
                "value": [
                    "Coding"
                ]
            },
            {
                "type": "rejectionResolution",
                "value": [
                    "Invalid",
                    "Duplicate",
                    "Unrequired"
                ]
            },
            {
                "type": "qaRootCause",
                "value": [
                    "Coding",
                    "Configuration",
                    "Regression",
                    "Data"
                ]
            }
        ]
    },
    {
        "tool": "Azure",
        "isKanban": false,
        "issues": [{
                "type": "story",
                "value": [
                    "User Story",
                    "Task",
                    "Feature",
                    "Epic"
                ]
            },
            {
                "type": "bug",
                "value": [
                    "Bug",
                    "Issue"
                ]
            },
            {
                "type": "epic",
                "value": [
                    "Epic"
                ]
            },
            {
                "type": "issuetype",
                "value": [
                    "User Story",
                    "Task",
                    "Feature",
                    "Issue",
                    "Bug",
                    "Epic"
                ]
            },
            {
                "type": "epic",
                "value": [
                    "Epic"
                ]
            },
            {
                "type": "uatdefect",
                "value": [
                    "UAT Defect"
                ]
            }
        ],
        "customfield": [{
                "type": "rootcause",
                "value": [
                    "RootCause"
                ]
            },
            {
                "type": "techdebt",
                "value": [
                    "Tech Debt"
                ]
            },
            {
                "type": "uat",
                "value": [
                    "UAT"
                ]
            },
            {
                "type": "timeCriticality",
                "value": [
                    "Time Criticality"
                ]
            },
            {
                "type": "wsjf",
                "value": [
                    "WSJF Score"
                ]
            },
            {
                "type": "businessValue",
                "value": [
                    "Business Value"
                ]
            },
            {
                "type": "riskReduction",
                "value": [
                    "Risk Reduction/Opportunity Enablement"
                ]
            },
            {
                "type": "jobSize",
                "value": [
                    "Effort"
                ]
            },
            {
                "type": "storypoint",
                "value": [
                    "Effort"
                ]
            }
        ],
        "workflow": [{
                "type": "dor",
                "value": [
                    "Active"
                ]
            },
            {
                "type": "dod",
                "value": [
                    "Resolved",
                    "Closed",
                    "Removed",
                    "Done"
                ]
            },
            {
                "type": "development",
                "value": [
                    "Active",
                    "In Development",
                    "In Analysis"
                ]
            },
            {
                "type": "qa",
                "value": [
                    "In Testing"
                ]
            },
            {
                "type": "firststatus",
                "value": [
                    "New",
                    "To do"
                ]
            },
            {
                "type": "rejection",
                "value": [
                    "Rejected",
                    "Closed"
                ]
            },
            {
                "type": "delivered",
                "value": [
                    "Closed",
                    "Ready for Delivery",
                    "Ready for Release"
                ]
            }
        ],
        "valuestoidentify": [{
                "type": "rootCauseValue",
                "value": [
                    "Coding"
                ]
            },
            {
                "type": "rejectionResolution",
                "value": [
                    "Invalid",
                    "Duplicate",
                    "Unrequired",
                    "Removed"
                ]
            },
            {
                "type": "qaRootCause",
                "value": [
                    "Coding",
                    "Configuration",
                    "Regression",
                    "Data"
                ]
            }
        ]
    },
    {
        "tool": "Jira",
        "templateName": "Custom Template",
        "templateCode": "9",
        "isKanban": true,
        "disabled": true
    },
    {
        "tool": "Jira",
        "templateName": "Custom Template",
        "templateCode": "10",
        "isKanban": false,
        "disabled": true
    },
    {
        "tool": "Azure",
        "isKanban": true,
        "issues": [{
                "type": "story",
                "value": [
                    "User Story",
                    "Task",
                    "Feature"
                ]
            },
            {
                "type": "bug",
                "value": [
                    "Bug",
                    "Issue"
                ]
            },
            {
                "type": "epic",
                "value": [
                    "Epic"
                ]
            },
            {
                "type": "issuetype",
                "value": [
                    "User Story",
                    "Task",
                    "Feature",
                    "Epic",
                    "Issue",
                    "Bug",
                    "Test Case",
                    "Epic"
                ]
            },
            {
                "type": "epic",
                "value": [
                    "Epic"
                ]
            },
            {
                "type": "uatdefect",
                "value": [
                    "UAT Defect",
                    "Bug"
                ]
            }
        ],
        "customfield": [{
                "type": "rootcause",
                "value": [
                    "RootCause"
                ]
            },
            {
                "type": "techdebt",
                "value": [
                    "Tech Debt"
                ]
            },
            {
                "type": "uat",
                "value": [
                    "UAT"
                ]
            },
            {
                "type": "timeCriticality",
                "value": [
                    "Time Criticality"
                ]
            },
            {
                "type": "wsjf",
                "value": [
                    "WSJF Score"
                ]
            },
            {
                "type": "businessValue",
                "value": [
                    "Business Value"
                ]
            },
            {
                "type": "riskReduction",
                "value": [
                    "Risk Reduction/Opportunity Enablement"
                ]
            },
            {
                "type": "jobSize",
                "value": [
                    "Effort"
                ]
            }
        ],
        "workflow": [{
                "type": "dor",
                "value": [
                    "Active"
                ]
            },
            {
                "type": "dod",
                "value": [
                    "Resolved",
                    "Closed"
                ]
            },
            {
                "type": "development",
                "value": [
                    "Active",
                    "In Development",
                    "In Analysis"
                ]
            },
            {
                "type": "qa",
                "value": [
                    "In Testing"
                ]
            },
            {
                "type": "firststatus",
                "value": [
                    "New"
                ]
            },
            {
                "type": "rejection",
                "value": [
                    "Rejected"
                ]
            },
            {
                "type": "delivered",
                "value": [
                    "Closed",
                    "Ready for Delivery",
                    "Ready for Release"
                ]
            },
            {
                "type": "ticketReopenStatus",
                "value": [
                    "Reopened"
                ]
            },
            {
                "type": "ticketResolvedStatus",
                "value": [
                    "Resolved",
                    "Closed"
                ]
            },
            {
                "type": "ticketClosedStatus",
                "value": [
                    "Closed"
                ]
            },
            {
                "type": "ticketWIPStatus",
                "value": [
                    "Active",
                    "In Progress",
                    "In Development"
                ]
            },
            {
                "type": "ticketRejectedStatus",
                "value": [
                    "Dropped",
                    "Rejected"
                ]
            },
            {
                "type": "ticketTriagedStatus",
                "value": [
                    "Active",
                    "In Progress",
                    "Accepted",
                    "In Development"
                ]
            }
        ],
        "valuestoidentify": [{
                "type": "rootCauseValue",
                "value": [
                    "Coding"
                ]
            },
            {
                "type": "rejectionResolution",
                "value": [
                    "Invalid",
                    "Duplicate",
                    "Unrequired"
                ]
            },
            {
                "type": "qaRootCause",
                "value": [
                    "Coding",
                    "Configuration",
                    "Regression",
                    "Data"
                ]
            }
        ]
    }

]);