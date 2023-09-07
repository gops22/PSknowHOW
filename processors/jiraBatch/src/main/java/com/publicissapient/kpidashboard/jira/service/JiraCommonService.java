package com.publicissapient.kpidashboard.jira.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.atlassian.jira.rest.client.api.domain.Status;
import com.google.common.collect.Lists;
import com.publicissapient.kpidashboard.common.model.application.ProjectVersion;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.publicissapient.kpidashboard.common.client.KerberosClient;
import com.publicissapient.kpidashboard.common.constant.CommonConstant;
import com.publicissapient.kpidashboard.common.constant.ProcessorConstants;
import com.publicissapient.kpidashboard.common.model.ProcessorExecutionTraceLog;
import com.publicissapient.kpidashboard.common.model.ToolCredential;
import com.publicissapient.kpidashboard.common.model.connection.Connection;
import com.publicissapient.kpidashboard.common.model.jira.KanbanIssueCustomHistory;
import com.publicissapient.kpidashboard.common.model.jira.KanbanJiraIssue;
import com.publicissapient.kpidashboard.common.repository.jira.KanbanJiraIssueHistoryRepository;
import com.publicissapient.kpidashboard.common.repository.jira.KanbanJiraIssueRepository;
import com.publicissapient.kpidashboard.common.service.AesEncryptionService;
import com.publicissapient.kpidashboard.common.service.ProcessorExecutionTraceLogService;
import com.publicissapient.kpidashboard.common.service.ToolCredentialProvider;
import com.publicissapient.kpidashboard.common.util.DateUtil;
import com.publicissapient.kpidashboard.jira.client.CustomAsynchronousIssueRestClient;
import com.publicissapient.kpidashboard.jira.client.ProcessorJiraRestClient;
import com.publicissapient.kpidashboard.jira.config.JiraProcessorConfig;
import com.publicissapient.kpidashboard.jira.constant.JiraConstants;
import com.publicissapient.kpidashboard.jira.helper.JiraHelper;
import com.publicissapient.kpidashboard.jira.model.JiraToolConfig;
import com.publicissapient.kpidashboard.jira.model.ProjectConfFieldMapping;

import io.atlassian.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JiraCommonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JiraCommonService.class);
	private static final String MSG_JIRA_CLIENT_SETUP_FAILED = "Jira client setup failed. No results obtained. Check your jira setup.";
	private static final String ERROR_MSG_401 = "Error 401 connecting to JIRA server, your credentials are probably wrong. Note: Ensure you are using JIRA user name not your email address.";
	private static final String ERROR_MSG_NO_RESULT_WAS_AVAILABLE = "No result was available from Jira unexpectedly - defaulting to blank response. The reason for this fault is the following : {}";
	private static final String NO_RESULT_QUERY = "No result available for query: {}";

	@Autowired
	private JiraProcessorConfig jiraProcessorConfig;

	@Autowired
	private ProcessorExecutionTraceLogService processorExecutionTraceLogService;

	private ProcessorJiraRestClient client;

	@Autowired
	private KanbanJiraIssueRepository kanbanJiraRepo;

	@Autowired
	ValidateData validateData;

	@Autowired
	private ToolCredentialProvider toolCredentialProvider;

	@Autowired
	private AesEncryptionService aesEncryptionService;

	@Autowired
	private KanbanJiraIssueHistoryRepository kanbanIssueHistoryRepo;

	public int getPageSize() {
		return jiraProcessorConfig.getPageSize();
	}

	public String getUserTimeZone(ProjectConfFieldMapping projectConfig, KerberosClient krb5Client) {
		String userTimeZone = StringUtils.EMPTY;
		try {
			JiraToolConfig jiraToolConfig = projectConfig.getJira();

			if (null != jiraToolConfig) {
				Optional<Connection> connectionOptional = projectConfig.getJira().getConnection();
				String username = connectionOptional.map(Connection::getUsername).orElse(null);
				URL url = getUrl(projectConfig, username);

				userTimeZone = parseUserTimeZone(getDataFromClient(projectConfig, url, krb5Client));
			}
		} catch (RestClientException rce) {
			log.error("Client exception when loading statuses", rce);
			throw rce;
		} catch (MalformedURLException mfe) {
			log.error("Malformed url for loading statuses", mfe);
		} catch (IOException ioe) {
			log.error("IOException", ioe);
		}

		return userTimeZone;
	}

	public String parseUserTimeZone(String timezoneObj) {
		String userTimeZone = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(timezoneObj)) {
			try {
				Object obj = new JSONParser().parse(timezoneObj);
				JSONArray userInfoList = new JSONArray();
				userInfoList.add(obj);
				for (Object userInfo : userInfoList) {
					JSONArray jsonUserInfo = (JSONArray) userInfo;
					for (Object timeZone : jsonUserInfo) {
						JSONObject timeZoneObj = (JSONObject) timeZone;
						userTimeZone = (String) timeZoneObj.get("timeZone");
					}
				}

			} catch (ParseException pe) {
				log.error("Parser exception when parsing statuses", pe);
			}
		}
		return userTimeZone;
	}

	private URL getUrl(ProjectConfFieldMapping projectConfig, String jiraUserName) throws MalformedURLException {

		Optional<Connection> connectionOptional = projectConfig.getJira().getConnection();
		boolean isCloudEnv = connectionOptional.map(Connection::isCloudEnv).orElse(false);
		String serverURL = jiraProcessorConfig.getJiraServerGetUserApi();
		if (isCloudEnv) {
			serverURL = jiraProcessorConfig.getJiraCloudGetUserApi();
		}

		String baseUrl = connectionOptional.map(Connection::getBaseUrl).orElse("");
		String apiEndPoint = connectionOptional.map(Connection::getApiEndPoint).orElse("");

		return new URL(baseUrl + (baseUrl.endsWith("/") ? "" : "/") + apiEndPoint
				+ (apiEndPoint.endsWith("/") ? "" : "/") + serverURL + jiraUserName);

	}

	public String getDataFromClient(ProjectConfFieldMapping projectConfig, URL url, KerberosClient krb5Client)
			throws IOException {
		Optional<Connection> connectionOptional = projectConfig.getJira().getConnection();
		boolean spenagoClient = connectionOptional.map(Connection::isJaasKrbAuth).orElse(false);
		if (spenagoClient) {
			HttpUriRequest request = RequestBuilder.get().setUri(url.toString())
					.setHeader(org.apache.http.HttpHeaders.ACCEPT, "application/json")
					.setHeader(org.apache.http.HttpHeaders.CONTENT_TYPE, "application/json").build();
			return krb5Client.getResponse(request);
		} else {
			return getDataFromServer(url, connectionOptional);
		}
	}

	public String getDataFromServer(URL url, Optional<Connection> connectionOptional) throws IOException {
		HttpURLConnection request = (HttpURLConnection) url.openConnection();

		String username = null;
		String password = null;

		if (connectionOptional.isPresent()) {
			Connection conn = connectionOptional.get();
			if (conn.isVault()) {
				ToolCredential toolCredential = toolCredentialProvider.findCredential(conn.getUsername());
				if (toolCredential != null) {
					username = toolCredential.getUsername();
					password = toolCredential.getPassword();
				}

			} else {
				username = connectionOptional.map(Connection::getUsername).orElse(null);
				password = decryptJiraPassword(connectionOptional.map(Connection::getPassword).orElse(null));
			}
		}
		request.setRequestProperty("Authorization", "Basic " + encodeCredentialsToBase64(username, password)); // NOSONAR
		request.connect();
		StringBuilder sb = new StringBuilder();
		try (InputStream in = (InputStream) request.getContent();
				BufferedReader inReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));) {
			int cp;
			while ((cp = inReader.read()) != -1) {
				sb.append((char) cp);
			}
		} catch (IOException ie) {
			log.error("Read exception when connecting to server {}", ie);
		}
		return sb.toString();
	}

	public String decryptJiraPassword(String encryptedPassword) {
		return aesEncryptionService.decrypt(encryptedPassword, jiraProcessorConfig.getAesEncryptionKey());
	}

	public String encodeCredentialsToBase64(String username, String password) {
		String cred = username + ":" + password;
		return Base64.getEncoder().encodeToString(cred.getBytes());
	}

	public boolean cleanCache() {
		boolean accountHierarchyCleaned = cacheRestClient(CommonConstant.CACHE_CLEAR_ENDPOINT,
				CommonConstant.CACHE_ACCOUNT_HIERARCHY);
		boolean kpiDataCleaned = cacheRestClient(CommonConstant.CACHE_CLEAR_ENDPOINT, CommonConstant.JIRA_KPI_CACHE);
		return accountHierarchyCleaned && kpiDataCleaned;
	}

	public boolean cacheRestClient(String cacheEndPoint, String cacheName) {
		boolean cleaned = false;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(jiraProcessorConfig.getCustomApiBaseUrl());
		uriBuilder.path("/");
		uriBuilder.path(cacheEndPoint);
		uriBuilder.path("/");
		uriBuilder.path(cacheName);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, entity, String.class);
		} catch (RuntimeException e) {
			LOGGER.error("[JIRA-CUSTOMAPI-CACHE-EVICT]. Error while consuming rest service", e);
		}

		if (null != response && response.getStatusCode().is2xxSuccessful()) {
			cleaned = true;
			LOGGER.info("[JIRA-CUSTOMAPI-CACHE-EVICT]. Successfully evicted cache {}", cacheName);
		} else {
			LOGGER.error("[JIRA-CUSTOMAPI-CACHE-EVICT]. Error while evicting cache {}", cacheName);
		}
		return cleaned;
	}

	// public void savingIssueLogs(int savedIssuesCount, List<JiraIssue>
	// jiraIssues, Instant startProcessingJiraIssues,
	// boolean isEpic, PSLogData psLogData) {
	// PSLogData saveIssueLog = new PSLogData();
	// saveIssueLog.setIssueAndDesc(jiraIssues.stream().map(JiraIssue::getNumber).collect(Collectors.toList()));
	// saveIssueLog.setTotalSavedIssues(String.valueOf(savedIssuesCount));
	// psLogData.setTotalSavedIssues(String.valueOf(savedIssuesCount));
	// psLogData.setTimeTaken(String.valueOf(Duration.between(startProcessingJiraIssues,
	// Instant.now()).toMillis()));
	// psLogData.setSprintListFetched(null);
	// psLogData.setTotalFetchedSprints(null);
	// if (!isEpic) {
	// saveIssueLog.setAction(CommonConstant.SAVED_ISSUES);
	// psLogData.setAction(CommonConstant.SAVED_ISSUES);
	// saveIssueLog.setTotalFetchedIssues(psLogData.getTotalFetchedIssues());
	// log.debug("Saved Issues for project {}",
	// MDC.get(CommonConstant.PROJECTNAME),
	// kv(CommonConstant.PSLOGDATA, saveIssueLog));
	// log.info("Processed Issues for project {}",
	// MDC.get(CommonConstant.PROJECTNAME),
	// kv(CommonConstant.PSLOGDATA, psLogData));
	// } else {
	// saveIssueLog.setAction(CommonConstant.SAVED_EPIC_ISSUES);
	// psLogData.setAction(CommonConstant.SAVED_EPIC_ISSUES);
	// saveIssueLog.setEpicIssuesFetched(psLogData.getEpicIssuesFetched());
	// log.debug("Saved Epic Issues for project {}",
	// MDC.get(CommonConstant.PROJECTNAME),
	// kv(CommonConstant.PSLOGDATA, saveIssueLog));
	// log.info("Processed Epic Issues for project {}",
	// MDC.get(CommonConstant.PROJECTNAME),
	// kv(CommonConstant.PSLOGDATA, psLogData));
	//
	// }
	// }

	public ProcessorExecutionTraceLog createTraceLog(ProjectConfFieldMapping projectConfig) {
		List<ProcessorExecutionTraceLog> traceLogs = processorExecutionTraceLogService
				.getTraceLogs(ProcessorConstants.JIRA, projectConfig.getBasicProjectConfigId().toHexString());
		ProcessorExecutionTraceLog processorExecutionTraceLog = null;

		if (CollectionUtils.isNotEmpty(traceLogs)) {
			processorExecutionTraceLog = traceLogs.get(0);
			if (null == processorExecutionTraceLog.getLastSuccessfulRun() || projectConfig.getProjectBasicConfig()
					.isSaveAssigneeDetails() != processorExecutionTraceLog.isLastEnableAssigneeToggleState()) {
				processorExecutionTraceLog.setLastSuccessfulRun(jiraProcessorConfig.getStartDate());
			}
		} else {
			processorExecutionTraceLog = new ProcessorExecutionTraceLog();
			processorExecutionTraceLog.setProcessorName(ProcessorConstants.JIRA);
			processorExecutionTraceLog.setBasicProjectConfigId(projectConfig.getBasicProjectConfigId().toHexString());
			processorExecutionTraceLog.setExecutionStartedAt(System.currentTimeMillis());
			processorExecutionTraceLog.setLastSuccessfulRun(jiraProcessorConfig.getStartDate());
		}
		return processorExecutionTraceLog;
	}

	public KanbanJiraIssue findOneKanbanIssueRepo(String issueId, String basicProjectConfigId) {
		List<KanbanJiraIssue> jiraIssues = kanbanJiraRepo
				.findByIssueIdAndBasicProjectConfigId(StringEscapeUtils.escapeHtml4(issueId), basicProjectConfigId);

		// Not sure of the state of the data
		if (jiraIssues.size() > 1) {
			log.warn("JIRA Processor | More than one collector item found for scopeId {}", issueId);
		}

		if (!jiraIssues.isEmpty()) {
			return jiraIssues.get(0);
		}

		return null;
	}

	/**
	 * Find kanban Jira Issue custom history object by issueId
	 *
	 * @param issueId
	 *            Jira issue ID
	 * @param basicProjectConfigId
	 *            basicProjectConfigId
	 * @return KanbanIssueCustomHistory Kanban history object corresponding to
	 *         issueId from DB
	 */
	public KanbanIssueCustomHistory findOneKanbanIssueCustomHistory(String issueId, String basicProjectConfigId) {
		List<KanbanIssueCustomHistory> jiraIssues = kanbanIssueHistoryRepo.findByStoryIDAndBasicProjectConfigId(issueId,
				basicProjectConfigId);
		// Not sure of the state of the data
		if (jiraIssues.size() > 1) {
			log.warn("JIRA Processor | Data issue More than one JIRA issue item found for id {}", issueId);
		}
		if (!jiraIssues.isEmpty()) {
			return jiraIssues.get(0);
		}

		return null;
	}

	public List<Issue> fetchIssuesBasedOnJql(ProjectConfFieldMapping projectConfig,
			ProcessorJiraRestClient clientIncoming, KerberosClient krb5Client, int pageNumber, String deltaDate) {

		client = clientIncoming;
		List<Issue> issues = new ArrayList<>();
		if (client == null) {
			log.error(MSG_JIRA_CLIENT_SETUP_FAILED);
		} else {
			try {
				// find deltaDate logic : processor successful run :
				// latestSuccessful run -1 day. First time : last n months data
				// defined in properties file

				String queryDate = DateUtil
						.dateTimeFormatter(DateUtil.stringToLocalDateTime(deltaDate, JiraConstants.QUERYDATEFORMAT)
								.minusDays(jiraProcessorConfig.getDaysToReduce()), JiraConstants.QUERYDATEFORMAT);

				SearchResult searchResult = getJqlIssues(projectConfig, queryDate, pageNumber);
				issues = JiraHelper.getIssuesFromResult(searchResult);

			} catch (InterruptedException e) {
				log.error("Interrupted exception thrown.", e);
			}
		}
		return issues;
	}

	private SearchResult getJqlIssues(ProjectConfFieldMapping projectConfig, String deltaDate, int pageStart)
			throws InterruptedException {
		SearchResult searchResult = null;
		String[] jiraIssueTypeNames = projectConfig.getFieldMapping().getJiraIssueTypeNames();
		if (client == null) {
			log.warn(MSG_JIRA_CLIENT_SETUP_FAILED);
		} else if (StringUtils.isEmpty(projectConfig.getProjectToolConfig().getProjectKey())
				|| StringUtils.isEmpty(projectConfig.getProjectToolConfig().getBoardQuery())
				|| null == jiraIssueTypeNames) {
			log.error(
					"Either Project key is empty or Jql Query not provided or jiraIssueTypeNames not configured in fieldmapping . key {} jql query {} ",
					projectConfig.getProjectToolConfig().getProjectKey(),
					projectConfig.getProjectToolConfig().getBoardQuery());
		} else {
			String issueTypes = Arrays.stream(jiraIssueTypeNames)
					.map(array -> "\"" + String.join("\", \"", array) + "\"").collect(Collectors.joining(", "));
			StringBuilder query = new StringBuilder("project in (")
					.append(projectConfig.getProjectToolConfig().getProjectKey()).append(") and ");
			try {
				String userQuery = projectConfig.getJira().getBoardQuery().toLowerCase()
						.split(JiraConstants.ORDERBY)[0];
				query.append(userQuery);
				query.append(" and issuetype in (" + issueTypes + " ) and updatedDate>='" + deltaDate + "' ");
				query.append(" order BY updated asc");

				log.info("jql query :{}", query);
				Promise<SearchResult> promisedRs = client.getProcessorSearchClient().searchJql(query.toString(),
						jiraProcessorConfig.getPageSize(), pageStart, JiraConstants.ISSUE_FIELD_SET);
				searchResult = promisedRs.claim();
			} catch (RestClientException e) {
				if (e.getStatusCode().isPresent() && e.getStatusCode().get() == 401) {
					log.error(ERROR_MSG_401);
				} else {
					log.info(NO_RESULT_QUERY, query);
					log.error(ERROR_MSG_NO_RESULT_WAS_AVAILABLE, e.getCause());
				}
			}

		}

		return searchResult;
	}

	public List<Issue> fetchIssueBasedOnBoard(ProjectConfFieldMapping projectConfig,
			ProcessorJiraRestClient clientIncoming, KerberosClient krb5Client, int pageNumber, String boardId,
			String deltaDate) {

		client = clientIncoming;
		List<Issue> issues = new ArrayList<>();
		if (client == null) {
			log.error(MSG_JIRA_CLIENT_SETUP_FAILED);
		} else {
			try {
				// find deltaDate logic : processor successful run :
				// latestSuccessful run -1 day. First time : last n months data
				// defined in properties file

				String queryDate = DateUtil
						.dateTimeFormatter(DateUtil.stringToLocalDateTime(deltaDate, JiraConstants.QUERYDATEFORMAT)
								.minusDays(jiraProcessorConfig.getDaysToReduce()), JiraConstants.QUERYDATEFORMAT);

				SearchResult searchResult = getBoardIssues(boardId, projectConfig, queryDate, pageNumber);
				issues = JiraHelper.getIssuesFromResult(searchResult);

			} catch (InterruptedException e) {
				log.error("Interrupted exception thrown.", e);
			}
		}
		return issues;
	}

	public SearchResult getBoardIssues(String boardId, ProjectConfFieldMapping projectConfig, String deltaDate,
			int pageStart) throws InterruptedException {
		SearchResult searchResult = null;
		String[] jiraIssueTypeNames = projectConfig.getFieldMapping().getJiraIssueTypeNames();
		if (client == null) {
			log.warn(MSG_JIRA_CLIENT_SETUP_FAILED);
		} else if (StringUtils.isEmpty(projectConfig.getProjectToolConfig().getProjectKey())
				|| null == jiraIssueTypeNames) {
			log.error("Either Project key is empty or jiraIssueTypeNames not provided. key {} ",
					projectConfig.getProjectToolConfig().getProjectKey());
		} else {
			String issueTypes = Arrays.stream(jiraIssueTypeNames)
					.map(array -> "\"" + String.join("\", \"", array) + "\"").collect(Collectors.joining(", "));
			String query = StringUtils.EMPTY;
			try {
				query = "issuetype in (" + issueTypes + " ) and updatedDate>='" + deltaDate
						+ "' order by updatedDate asc";
				CustomAsynchronousIssueRestClient issueRestClient = client.getCustomIssueClient();
				Promise<SearchResult> promisedRs = issueRestClient.searchBoardIssue(boardId, query,
						jiraProcessorConfig.getPageSize(), pageStart, JiraConstants.ISSUE_FIELD_SET);
				searchResult = promisedRs.claim();

			} catch (RestClientException e) {
				if (e.getStatusCode().isPresent() && e.getStatusCode().get() == 401) {
					log.error(ERROR_MSG_401);
				} else {
					log.error(ERROR_MSG_NO_RESULT_WAS_AVAILABLE, e.getCause());
				}
			}

		}

		return searchResult;
	}

	public List<ProjectVersion> getVersion(ProjectConfFieldMapping projectConfig, KerberosClient krb5Client) {
		List<ProjectVersion> projectVersionList = new ArrayList<>();
		try {
			JiraToolConfig jiraToolConfig = projectConfig.getJira();
			if (null != jiraToolConfig) {
				URL url = getVersionUrl(projectConfig);
				parseVersionData(getDataFromClient(projectConfig, url, krb5Client ), projectVersionList);
			}
		} catch (RestClientException rce) {
			log.error("Client exception when fetching versions " + rce);
		} catch (MalformedURLException mfe) {
			log.error("Malformed url for fetching versions", mfe);
		} catch (IOException ioe) {
			log.error("IOException", ioe);
		}
		return projectVersionList;
	}

	private URL getVersionUrl(ProjectConfFieldMapping projectConfig)
			throws MalformedURLException {

		Optional<Connection> connectionOptional = projectConfig.getJira().getConnection();
		boolean isCloudEnv = connectionOptional.map(Connection::isCloudEnv).orElse(false);
		String serverURL = jiraProcessorConfig.getJiraVersionApi();
		if (isCloudEnv) {
			serverURL = jiraProcessorConfig.getJiraCloudVersionApi();
		}
		serverURL = serverURL.replace("{projectKey}", projectConfig.getJira().getProjectKey());
		String baseUrl = connectionOptional.map(Connection::getBaseUrl).orElse("");
		return new URL(baseUrl + (baseUrl.endsWith("/") ? "" : "/") + serverURL);

	}

	private void parseVersionData(String dataFromServer, List<ProjectVersion> projectVersionDetailList) {
		if (StringUtils.isNotBlank(dataFromServer)) {
			try {
				JSONArray obj = (JSONArray) new JSONParser().parse(dataFromServer);
				if (null != obj) {
					((JSONArray) new JSONParser().parse(dataFromServer)).forEach(values -> {
						ProjectVersion projectVersion = new ProjectVersion();
						projectVersion.setId(
								Long.valueOf(Objects.requireNonNull(getOptionalString((JSONObject) values, "id"))));
						projectVersion.setName(getOptionalString((JSONObject) values, "name"));
						projectVersion
								.setArchived(Boolean.parseBoolean(getOptionalString((JSONObject) values, "archived")));
						projectVersion
								.setReleased(Boolean.parseBoolean(getOptionalString((JSONObject) values, "released")));
						if (getOptionalString((JSONObject) values, "startDate") != null) {
							projectVersion.setStartDate(DateUtil.stringToDateTime(Objects.requireNonNull(getOptionalString((JSONObject) values, "startDate")),"yyyy-MM-dd"));
						}
						if (getOptionalString((JSONObject) values, "releaseDate") != null) {
							projectVersion.setReleaseDate(DateUtil.stringToDateTime(Objects.requireNonNull(getOptionalString((JSONObject) values, "releaseDate")),"yyyy-MM-dd"));
						}
						projectVersionDetailList.add(projectVersion);
					});
				}
			} catch (Exception pe) {
				log.error("Parser exception when parsing versions", pe);
			}

		}
	}

	private String getOptionalString(final JSONObject jsonObject, final String attributeName) {
		final Object res = jsonObject.get(attributeName);
		if (res == null) {
			return null;
		}
		return res.toString();
	}

}
