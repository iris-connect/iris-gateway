package de.healthIMIS.iris.client.data_submission.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Additional informations about the last contact with the queried person.
 */
@Schema(description = "Additional informations about the last contact with the queried person.")
@Valid
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-18T08:11:24.698Z[GMT]")

public class ContactPersonContactInformation {

	@JsonProperty("date")
	private LocalDate date = null;

	@JsonProperty("contactCategory")
	private ContactCategory contactCategory = null;

	@JsonProperty("basicConditions")
	private String basicConditions = null;

	public ContactPersonContactInformation date(LocalDate date) {
		this.date = date;
		return this;
	}

	/**
	 * Get date
	 * 
	 * @return date
	 **/
	@Schema(description = "")

	@Valid
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public ContactPersonContactInformation contactCategory(ContactCategory contactCategory) {
		this.contactCategory = contactCategory;
		return this;
	}

	/**
	 * Get contactCategory
	 * 
	 * @return contactCategory
	 **/
	@Schema(description = "")

	@Valid
	public ContactCategory getContactCategory() {
		return contactCategory;
	}

	public void setContactCategory(ContactCategory contactCategory) {
		this.contactCategory = contactCategory;
	}

	public ContactPersonContactInformation basicConditions(String basicConditions) {
		this.basicConditions = basicConditions;
		return this;
	}

	/**
	 * Informations about the basic conditions such as: from, to, place, inside|outside, mask yes|no, distance >=|< 1,5m, ventilated yes|no,
	 * remarks.
	 * 
	 * @return basicConditions
	 **/
	@Schema(
		description = "Informations about the basic conditions such as: from, to, place, inside|outside, mask yes|no, distance >=|< 1,5m, ventilated yes|no, remarks.")

	public String getBasicConditions() {
		return basicConditions;
	}

	public void setBasicConditions(String basicConditions) {
		this.basicConditions = basicConditions;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ContactPersonContactInformation contactPersonContactInformation = (ContactPersonContactInformation) o;
		return Objects.equals(this.date, contactPersonContactInformation.date)
			&& Objects.equals(this.contactCategory, contactPersonContactInformation.contactCategory)
			&& Objects.equals(this.basicConditions, contactPersonContactInformation.basicConditions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, contactCategory, basicConditions);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ContactPersonContactInformation {\n");

		sb.append("    date: ").append(toIndentedString(date)).append("\n");
		sb.append("    contactCategory: ").append(toIndentedString(contactCategory)).append("\n");
		sb.append("    basicConditions: ").append(toIndentedString(basicConditions)).append("\n");
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