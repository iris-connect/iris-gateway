/*
 * SORMAS REST API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.55.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package de.healthIMIS.sormas.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.healthIMIS.sormas.client.model.CampaignFormTranslation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * CampaignFormTranslations
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen",
		date = "2021-01-28T11:46:54.705673+01:00[Europe/Berlin]")
public class CampaignFormTranslations {
	@JsonProperty("languageCode")
	private String languageCode = null;

	@JsonProperty("translations")
	private List<CampaignFormTranslation> translations = null;

	public CampaignFormTranslations languageCode(String languageCode) {
		this.languageCode = languageCode;
		return this;
	}

	/**
	 * Get languageCode
	 * 
	 * @return languageCode
	 **/
	@Schema(description = "")
	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public CampaignFormTranslations translations(List<CampaignFormTranslation> translations) {
		this.translations = translations;
		return this;
	}

	public CampaignFormTranslations addTranslationsItem(CampaignFormTranslation translationsItem) {
		if (this.translations == null) {
			this.translations = new ArrayList<>();
		}
		this.translations.add(translationsItem);
		return this;
	}

	/**
	 * Get translations
	 * 
	 * @return translations
	 **/
	@Schema(description = "")
	public List<CampaignFormTranslation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<CampaignFormTranslation> translations) {
		this.translations = translations;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CampaignFormTranslations campaignFormTranslations = (CampaignFormTranslations) o;
		return Objects.equals(this.languageCode, campaignFormTranslations.languageCode)
				&& Objects.equals(this.translations, campaignFormTranslations.translations);
	}

	@Override
	public int hashCode() {
		return Objects.hash(languageCode, translations);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CampaignFormTranslations {\n");

		sb.append("    languageCode: ").append(toIndentedString(languageCode)).append("\n");
		sb.append("    translations: ").append(toIndentedString(translations)).append("\n");
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
