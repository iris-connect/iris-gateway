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
package de.healthIMIS.iris.public_server.data_submission.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Basic data type of a data submission which contains the unencrypted metadata needed for processing.
 */
@Schema(description = "Basic data type of a data submission which contains the unencrypted metadata needed for processing.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-18T08:11:24.698Z[GMT]")

public class DataSubmissionDto {

	@JsonProperty("checkCode")
	@Valid
	private List<String> checkCode = new ArrayList<String>();

	@JsonProperty("salt")
	private String salt = null;

	@JsonProperty("keyReferenz")
	private String keyReferenz = null;

	public DataSubmissionDto checkCode(List<String> checkCode) {
		this.checkCode = checkCode;
		return this;
	}

	public DataSubmissionDto addCheckCodeItem(String checkCodeItem) {
		this.checkCode.add(checkCodeItem);
		return this;
	}

	/**
	 * An array of up to three check codes, one of them must be correct for the data to be accepted. For details, please refer to section
	 * \"Prüfcode\" in concept at https://github.com/healthIMIS/IRIS-Concept/releases/latest
	 * 
	 * @return checkCode
	 **/
	@Schema(required = true,
		description = "An array of up to three check codes, one of them must be correct for the data to be accepted. For details, please refer to section \"Prüfcode\" in concept at https://github.com/healthIMIS/IRIS-Concept/releases/latest")
	@NotNull

	public List<String> getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(List<String> checkCode) {
		this.checkCode = checkCode;
	}

	public DataSubmissionDto salt(String salt) {
		this.salt = salt;
		return this;
	}

	/**
	 * Salt used for encryption.
	 * 
	 * @return salt
	 **/
	@Schema(required = true, description = "Salt used for encryption.")
	@NotNull

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public DataSubmissionDto keyReferenz(String keyReferenz) {
		this.keyReferenz = keyReferenz;
		return this;
	}

	/**
	 * Reference to the used key.
	 * 
	 * @return keyReferenz
	 **/
	@Schema(required = true, description = "Reference to the used key.")
	@NotNull

	public String getKeyReferenz() {
		return keyReferenz;
	}

	public void setKeyReferenz(String keyReferenz) {
		this.keyReferenz = keyReferenz;
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
		return Objects.equals(this.checkCode, dataSubmission.checkCode)
			&& Objects.equals(this.salt, dataSubmission.salt)
			&& Objects.equals(this.keyReferenz, dataSubmission.keyReferenz);
	}

	@Override
	public int hashCode() {
		return Objects.hash(checkCode, salt, keyReferenz);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class DataSubmission {\n");

		sb.append("    checkCode: ").append(toIndentedString(checkCode)).append("\n");
		sb.append("    salt: ").append(toIndentedString(salt)).append("\n");
		sb.append("    keyReferenz: ").append(toIndentedString(keyReferenz)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
