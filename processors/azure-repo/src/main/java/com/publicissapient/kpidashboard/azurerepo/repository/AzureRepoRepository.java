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

package com.publicissapient.kpidashboard.azurerepo.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;

import com.publicissapient.kpidashboard.azurerepo.model.AzureRepoModel;
import com.publicissapient.kpidashboard.common.repository.generic.ProcessorItemRepository;

/**
 * AzurerepoRepoRepository is used for to process AzurerepoRepo.
 * 
 * @see AzureRepoModel
 */
public interface AzureRepoRepository extends ProcessorItemRepository<AzureRepoModel> {

	/**
	 * Represents a function that accepts one input arguments and returns list of
	 * AzurerepoRepo.
	 *
	 * @param processorId
	 *            the processor id
	 * @return AzurerepoRepo list of AzurerepoRepo
	 */
	@Query("{ 'processorId' : ?0, 'isActive': true}")
	List<AzureRepoModel> findActiveRepos(ObjectId processorId);

}
