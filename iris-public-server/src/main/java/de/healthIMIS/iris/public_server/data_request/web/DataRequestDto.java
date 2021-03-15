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
package de.healthIMIS.iris.public_server.data_request.web;

import java.time.Instant;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A data request with all parameters relevant for the data submission.
 */
@Schema(description = "A data request with all parameters relevant for the data submission.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-18T08:11:24.698Z[GMT]")

public class DataRequestDto {

	@JsonProperty("healthDepartment")
	private String healthDepartment = null;

	@JsonProperty("createdAt")
	private Instant createdAt = null;

	@JsonProperty("keyOfHealthDepartment")
	private String keyOfHealthDepartment = null;

	@JsonProperty("keyReferenz")
	private String keyReferenz = null;

	@JsonProperty("start")
	private Instant start = null;

	@JsonProperty("end")
	private Instant end = null;

	@JsonProperty("requestDetails")
	private String requestDetails = null;

	public DataRequestDto healthDepartment(String healthDepartment) {
		this.healthDepartment = healthDepartment;
		return this;
	}

	/**
	 * Name of the requesting health department.
	 * 
	 * @return healthDepartment
	 **/
	@Schema(required = true, description = "Name of the requesting health department.")
	@NotNull

	public String getHealthDepartment() {
		return healthDepartment;
	}

	public void setHealthDepartment(String healthDepartment) {
		this.healthDepartment = healthDepartment;
	}

	public DataRequestDto createdAt(Instant createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	/**
	 * Creation time of the data request.
	 * 
	 * @return createdAt
	 **/
	@Schema(description = "Creation time of the data request.")

	@Valid
	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public DataRequestDto keyOfHealthDepartment(String keyOfHealthDepartment) {
		this.keyOfHealthDepartment = keyOfHealthDepartment;
		return this;
	}

	/**
	 * The key of the requesting health department that must be used for encryption.
	 * 
	 * @return keyOfHealthDepartment
	 **/
	@Schema(description = "The key of the requesting health department that must be used for encryption.")

	public String getKeyOfHealthDepartment() {
		return keyOfHealthDepartment;
	}

	public void setKeyOfHealthDepartment(String keyOfHealthDepartment) {
		this.keyOfHealthDepartment = keyOfHealthDepartment;
	}

	public DataRequestDto keyReferenz(String keyReferenz) {
		this.keyReferenz = keyReferenz;
		return this;
	}

	/**
	 * The key of the requesting health department that must be used for encryption.
	 * 
	 * @return keyReferenz
	 **/
	@Schema(description = "The key of the requesting health department that must be used for encryption.")

	public String getKeyReferenz() {
		return keyReferenz;
	}

	public void setKeyReferenz(String keyReferenz) {
		this.keyReferenz = keyReferenz;
	}

	public DataRequestDto start(Instant start) {
		this.start = start;
		return this;
	}

	/**
	 * The start time for which data should be submitted with this request.
	 * 
	 * @return start
	 **/
	@Schema(required = true, description = "The start time for which data should be submitted with this request.")
	@NotNull

	@Valid
	public Instant getStart() {
		return start;
	}

	public void setStart(Instant start) {
		this.start = start;
	}

	public DataRequestDto end(Instant end) {
		this.end = end;
		return this;
	}

	/**
	 * The end time for which data should be submitted with this request.
	 * 
	 * @return end
	 **/
	@Schema(description = "The end time for which data should be submitted with this request.")

	@Valid
	public Instant getEnd() {
		return end;
	}

	public void setEnd(Instant end) {
		this.end = end;
	}

	public DataRequestDto requestDetails(String requestDetails) {
		this.requestDetails = requestDetails;
		return this;
	}

	/**
	 * Details of the data request, specifying it in more detail and narrowing down the data to be provided (e.g. table and environment,
	 * seat, rank, ...).
	 * 
	 * @return end
	 **/
	@Schema(
		description = "Details of the data request, specifying it in more detail and narrowing down the data to be provided (e.g. table and environment, seat, rank, ...).")

	@Valid
	public String getRequestDetails() {
		return requestDetails;
	}

	public void setRequestDetails(String requestDetails) {
		this.requestDetails = requestDetails;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DataRequestDto dataRequest = (DataRequestDto) o;
		return Objects.equals(this.healthDepartment, dataRequest.healthDepartment)
			&& Objects.equals(this.createdAt, dataRequest.createdAt)
			&& Objects.equals(this.keyOfHealthDepartment, dataRequest.keyOfHealthDepartment)
			&& Objects.equals(this.start, dataRequest.start)
			&& Objects.equals(this.end, dataRequest.end);
	}

	@Override
	public int hashCode() {
		return Objects.hash(healthDepartment, createdAt, keyOfHealthDepartment, start, end);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class DataRequest {\n");

		sb.append("    healthDepartment: ").append(toIndentedString(healthDepartment)).append("\n");
		sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
		sb.append("    keyOfHealthDepartment: ").append(toIndentedString(keyOfHealthDepartment)).append("\n");
		sb.append("    start: ").append(toIndentedString(start)).append("\n");
		sb.append("    end: ").append(toIndentedString(end)).append("\n");
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
