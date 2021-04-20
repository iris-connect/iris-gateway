/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.healthIMIS.iris.public_server.data_submission.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Basic data type of a data submission which contains the unencrypted metadata needed for processing.
 */
@Schema(
		description = "Basic data type of a data submission which contains the unencrypted metadata needed for processing.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
		date = "2021-02-18T08:11:24.698Z[GMT]")

public class DataSubmissionDto {

	@JsonProperty("secret")
	private String secret = null;

	@JsonProperty("keyReference")
	private String keyReference = null;

	public DataSubmissionDto secret(String secret) {
		this.secret = secret;
		return this;
	}

	/**
	 * The encrypted secret key for encryption.
	 * 
	 * @return secret
	 **/
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public DataSubmissionDto keyReference(String keyReference) {
		this.keyReference = keyReference;
		return this;
	}

	/**
	 * Reference to the used key.
	 * 
	 * @return keyReference
	 **/
	@Schema(required = true, description = "Reference to the used key.")
	@NotNull

	public String getKeyReference() {
		return keyReference;
	}

	public void setKeyReference(String keyReference) {
		this.keyReference = keyReference;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DataSubmissionDto dataSubmission = (DataSubmissionDto) o;
		return Objects.equals(this.secret, dataSubmission.secret)
				&& Objects.equals(this.keyReference, dataSubmission.keyReference);
	}

	@Override
	public int hashCode() {
		return Objects.hash(secret, keyReference);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class DataSubmission {\n");

		sb.append("    secret: ").append(toIndentedString(secret)).append("\n");
		sb.append("    keyReference: ").append(toIndentedString(keyReference)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
