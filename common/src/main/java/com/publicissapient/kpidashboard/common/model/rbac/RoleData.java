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

package com.publicissapient.kpidashboard.common.model.rbac;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the role data.
 */
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "roles")
public class RoleData {

	@Id
	private ObjectId id;

	@Field("roleName")
	private String roleName;

	@Field("description")
	private String roleDescription;

	@Field("createdDate")
	private Date createdDate;

	@Field("lastModifiedDate")
	private Date lastModifiedDate;

	@Field("isDeleted")
	private String isDeleted = "False";

	@Field("permissions")
	private List<Permissions> permissions;

	@Field("displayName")
	private String displayName;

	/**
	 * Creates a RoleData Object with id
	 */
	public RoleData() {
		this.id = new ObjectId();
		this.createdDate = new Date();
		this.setLastModifiedDate(new Date());
	}

	/**
	 * Checks if the parameter object is equal to the class object
	 *
	 * @param obj
	 * @return boolean true or false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		RoleData other = (RoleData) obj;
		if (roleName == null) {
			if (other.roleName != null) {
				return false;
			}
		} else if (!roleName.equals(other.roleName)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		return result;
	}

}
