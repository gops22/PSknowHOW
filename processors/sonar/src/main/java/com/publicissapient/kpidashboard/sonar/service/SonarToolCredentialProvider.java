package com.publicissapient.kpidashboard.sonar.service;

import org.springframework.stereotype.Service;

import com.publicissapient.kpidashboard.common.model.ToolCredential;
import com.publicissapient.kpidashboard.common.service.ToolCredentialProvider;

@Service
public class SonarToolCredentialProvider implements ToolCredentialProvider {
	@Override
	public ToolCredential findCredential(String credRef) {
		return null;
	}
}
