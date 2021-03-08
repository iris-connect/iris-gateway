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
import de.healthIMIS.sormas.client.model.PreviousHospitalizationDto;
import de.healthIMIS.sormas.client.model.YesNoUnknown;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
/**
 * HospitalizationDto
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-01-28T11:46:54.705673+01:00[Europe/Berlin]")
public class HospitalizationDto {
  @JsonProperty("creationDate")
  private Instant creationDate = null;

  @JsonProperty("changeDate")
  private Instant changeDate = null;

  @JsonProperty("uuid")
  private String uuid = null;

  @JsonProperty("admittedToHealthFacility")
  private YesNoUnknown admittedToHealthFacility = null;

  @JsonProperty("admissionDate")
  private Instant admissionDate = null;

  @JsonProperty("dischargeDate")
  private Instant dischargeDate = null;

  @JsonProperty("isolated")
  private YesNoUnknown isolated = null;

  @JsonProperty("isolationDate")
  private Instant isolationDate = null;

  @JsonProperty("leftAgainstAdvice")
  private YesNoUnknown leftAgainstAdvice = null;

  @JsonProperty("hospitalizedPreviously")
  private YesNoUnknown hospitalizedPreviously = null;

  @JsonProperty("previousHospitalizations")
  private List<PreviousHospitalizationDto> previousHospitalizations = null;

  @JsonProperty("intensiveCareUnit")
  private YesNoUnknown intensiveCareUnit = null;

  @JsonProperty("intensiveCareUnitStart")
  private Instant intensiveCareUnitStart = null;

  @JsonProperty("intensiveCareUnitEnd")
  private Instant intensiveCareUnitEnd = null;

  public HospitalizationDto creationDate(Instant creationDate) {
    this.creationDate = creationDate;
    return this;
  }

   /**
   * Get creationDate
   * @return creationDate
  **/
  @Schema(description = "")
  public Instant getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Instant creationDate) {
    this.creationDate = creationDate;
  }

  public HospitalizationDto changeDate(Instant changeDate) {
    this.changeDate = changeDate;
    return this;
  }

   /**
   * Get changeDate
   * @return changeDate
  **/
  @Schema(description = "")
  public Instant getChangeDate() {
    return changeDate;
  }

  public void setChangeDate(Instant changeDate) {
    this.changeDate = changeDate;
  }

  public HospitalizationDto uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

   /**
   * Get uuid
   * @return uuid
  **/
  @Schema(description = "")
  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public HospitalizationDto admittedToHealthFacility(YesNoUnknown admittedToHealthFacility) {
    this.admittedToHealthFacility = admittedToHealthFacility;
    return this;
  }

   /**
   * Get admittedToHealthFacility
   * @return admittedToHealthFacility
  **/
  @Schema(description = "")
  public YesNoUnknown getAdmittedToHealthFacility() {
    return admittedToHealthFacility;
  }

  public void setAdmittedToHealthFacility(YesNoUnknown admittedToHealthFacility) {
    this.admittedToHealthFacility = admittedToHealthFacility;
  }

  public HospitalizationDto admissionDate(Instant admissionDate) {
    this.admissionDate = admissionDate;
    return this;
  }

   /**
   * Get admissionDate
   * @return admissionDate
  **/
  @Schema(description = "")
  public Instant getAdmissionDate() {
    return admissionDate;
  }

  public void setAdmissionDate(Instant admissionDate) {
    this.admissionDate = admissionDate;
  }

  public HospitalizationDto dischargeDate(Instant dischargeDate) {
    this.dischargeDate = dischargeDate;
    return this;
  }

   /**
   * Get dischargeDate
   * @return dischargeDate
  **/
  @Schema(description = "")
  public Instant getDischargeDate() {
    return dischargeDate;
  }

  public void setDischargeDate(Instant dischargeDate) {
    this.dischargeDate = dischargeDate;
  }

  public HospitalizationDto isolated(YesNoUnknown isolated) {
    this.isolated = isolated;
    return this;
  }

   /**
   * Get isolated
   * @return isolated
  **/
  @Schema(description = "")
  public YesNoUnknown getIsolated() {
    return isolated;
  }

  public void setIsolated(YesNoUnknown isolated) {
    this.isolated = isolated;
  }

  public HospitalizationDto isolationDate(Instant isolationDate) {
    this.isolationDate = isolationDate;
    return this;
  }

   /**
   * Get isolationDate
   * @return isolationDate
  **/
  @Schema(description = "")
  public Instant getIsolationDate() {
    return isolationDate;
  }

  public void setIsolationDate(Instant isolationDate) {
    this.isolationDate = isolationDate;
  }

  public HospitalizationDto leftAgainstAdvice(YesNoUnknown leftAgainstAdvice) {
    this.leftAgainstAdvice = leftAgainstAdvice;
    return this;
  }

   /**
   * Get leftAgainstAdvice
   * @return leftAgainstAdvice
  **/
  @Schema(description = "")
  public YesNoUnknown getLeftAgainstAdvice() {
    return leftAgainstAdvice;
  }

  public void setLeftAgainstAdvice(YesNoUnknown leftAgainstAdvice) {
    this.leftAgainstAdvice = leftAgainstAdvice;
  }

  public HospitalizationDto hospitalizedPreviously(YesNoUnknown hospitalizedPreviously) {
    this.hospitalizedPreviously = hospitalizedPreviously;
    return this;
  }

   /**
   * Get hospitalizedPreviously
   * @return hospitalizedPreviously
  **/
  @Schema(description = "")
  public YesNoUnknown getHospitalizedPreviously() {
    return hospitalizedPreviously;
  }

  public void setHospitalizedPreviously(YesNoUnknown hospitalizedPreviously) {
    this.hospitalizedPreviously = hospitalizedPreviously;
  }

  public HospitalizationDto previousHospitalizations(List<PreviousHospitalizationDto> previousHospitalizations) {
    this.previousHospitalizations = previousHospitalizations;
    return this;
  }

  public HospitalizationDto addPreviousHospitalizationsItem(PreviousHospitalizationDto previousHospitalizationsItem) {
    if (this.previousHospitalizations == null) {
      this.previousHospitalizations = new ArrayList<>();
    }
    this.previousHospitalizations.add(previousHospitalizationsItem);
    return this;
  }

   /**
   * Get previousHospitalizations
   * @return previousHospitalizations
  **/
  @Schema(description = "")
  public List<PreviousHospitalizationDto> getPreviousHospitalizations() {
    return previousHospitalizations;
  }

  public void setPreviousHospitalizations(List<PreviousHospitalizationDto> previousHospitalizations) {
    this.previousHospitalizations = previousHospitalizations;
  }

  public HospitalizationDto intensiveCareUnit(YesNoUnknown intensiveCareUnit) {
    this.intensiveCareUnit = intensiveCareUnit;
    return this;
  }

   /**
   * Get intensiveCareUnit
   * @return intensiveCareUnit
  **/
  @Schema(description = "")
  public YesNoUnknown getIntensiveCareUnit() {
    return intensiveCareUnit;
  }

  public void setIntensiveCareUnit(YesNoUnknown intensiveCareUnit) {
    this.intensiveCareUnit = intensiveCareUnit;
  }

  public HospitalizationDto intensiveCareUnitStart(Instant intensiveCareUnitStart) {
    this.intensiveCareUnitStart = intensiveCareUnitStart;
    return this;
  }

   /**
   * Get intensiveCareUnitStart
   * @return intensiveCareUnitStart
  **/
  @Schema(description = "")
  public Instant getIntensiveCareUnitStart() {
    return intensiveCareUnitStart;
  }

  public void setIntensiveCareUnitStart(Instant intensiveCareUnitStart) {
    this.intensiveCareUnitStart = intensiveCareUnitStart;
  }

  public HospitalizationDto intensiveCareUnitEnd(Instant intensiveCareUnitEnd) {
    this.intensiveCareUnitEnd = intensiveCareUnitEnd;
    return this;
  }

   /**
   * Get intensiveCareUnitEnd
   * @return intensiveCareUnitEnd
  **/
  @Schema(description = "")
  public Instant getIntensiveCareUnitEnd() {
    return intensiveCareUnitEnd;
  }

  public void setIntensiveCareUnitEnd(Instant intensiveCareUnitEnd) {
    this.intensiveCareUnitEnd = intensiveCareUnitEnd;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HospitalizationDto hospitalizationDto = (HospitalizationDto) o;
    return Objects.equals(this.creationDate, hospitalizationDto.creationDate) &&
        Objects.equals(this.changeDate, hospitalizationDto.changeDate) &&
        Objects.equals(this.uuid, hospitalizationDto.uuid) &&
        Objects.equals(this.admittedToHealthFacility, hospitalizationDto.admittedToHealthFacility) &&
        Objects.equals(this.admissionDate, hospitalizationDto.admissionDate) &&
        Objects.equals(this.dischargeDate, hospitalizationDto.dischargeDate) &&
        Objects.equals(this.isolated, hospitalizationDto.isolated) &&
        Objects.equals(this.isolationDate, hospitalizationDto.isolationDate) &&
        Objects.equals(this.leftAgainstAdvice, hospitalizationDto.leftAgainstAdvice) &&
        Objects.equals(this.hospitalizedPreviously, hospitalizationDto.hospitalizedPreviously) &&
        Objects.equals(this.previousHospitalizations, hospitalizationDto.previousHospitalizations) &&
        Objects.equals(this.intensiveCareUnit, hospitalizationDto.intensiveCareUnit) &&
        Objects.equals(this.intensiveCareUnitStart, hospitalizationDto.intensiveCareUnitStart) &&
        Objects.equals(this.intensiveCareUnitEnd, hospitalizationDto.intensiveCareUnitEnd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(creationDate, changeDate, uuid, admittedToHealthFacility, admissionDate, dischargeDate, isolated, isolationDate, leftAgainstAdvice, hospitalizedPreviously, previousHospitalizations, intensiveCareUnit, intensiveCareUnitStart, intensiveCareUnitEnd);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HospitalizationDto {\n");
    
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    changeDate: ").append(toIndentedString(changeDate)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    admittedToHealthFacility: ").append(toIndentedString(admittedToHealthFacility)).append("\n");
    sb.append("    admissionDate: ").append(toIndentedString(admissionDate)).append("\n");
    sb.append("    dischargeDate: ").append(toIndentedString(dischargeDate)).append("\n");
    sb.append("    isolated: ").append(toIndentedString(isolated)).append("\n");
    sb.append("    isolationDate: ").append(toIndentedString(isolationDate)).append("\n");
    sb.append("    leftAgainstAdvice: ").append(toIndentedString(leftAgainstAdvice)).append("\n");
    sb.append("    hospitalizedPreviously: ").append(toIndentedString(hospitalizedPreviously)).append("\n");
    sb.append("    previousHospitalizations: ").append(toIndentedString(previousHospitalizations)).append("\n");
    sb.append("    intensiveCareUnit: ").append(toIndentedString(intensiveCareUnit)).append("\n");
    sb.append("    intensiveCareUnitStart: ").append(toIndentedString(intensiveCareUnitStart)).append("\n");
    sb.append("    intensiveCareUnitEnd: ").append(toIndentedString(intensiveCareUnitEnd)).append("\n");
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
