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

package com.publicissapient.kpidashboard.apis.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.publicissapient.kpidashboard.common.repository.rbac.UserTokenReopository;

/**
 * Implementation of {@link UserTokenDeletionService}
 * 
 * @author anisingh4
 */
@Service
public class UserTokenDeletionServiceImpl implements UserTokenDeletionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenDeletionServiceImpl.class);

	@Autowired
	private UserTokenReopository userTokenReopository;

	@Override
	public void deleteUserDetails(String userToken) {

		LOGGER.info("UserTokenDeletionServiceImpl::deleteUserDetails start");
		userTokenReopository.deleteByUserToken(userToken);
		LOGGER.info("UserTokenDeletionServiceImpl::deleteUserDetails end");

	}

	public void invalidateSession(String userName) {

		LOGGER.info("UserTokenDeletionServiceImpl::deleteUserToken start");
		userTokenReopository.deleteByuserName(userName);
		LOGGER.info("UserTokenDeletionServiceImpl::deleteUserToken end");
	}

}
