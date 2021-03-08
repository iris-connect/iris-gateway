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
import de.healthIMIS.sormas.client.model.AnimalCondition;
import de.healthIMIS.sormas.client.model.AnimalContactType;
import de.healthIMIS.sormas.client.model.ContactReferenceDto;
import de.healthIMIS.sormas.client.model.ExposureType;
import de.healthIMIS.sormas.client.model.GatheringType;
import de.healthIMIS.sormas.client.model.HabitationType;
import de.healthIMIS.sormas.client.model.LocationDto;
import de.healthIMIS.sormas.client.model.MeansOfTransport;
import de.healthIMIS.sormas.client.model.PatientExpositionRole;
import de.healthIMIS.sormas.client.model.TypeOfAnimal;
import de.healthIMIS.sormas.client.model.TypeOfPlace;
import de.healthIMIS.sormas.client.model.UserReferenceDto;
import de.healthIMIS.sormas.client.model.WaterSource;
import de.healthIMIS.sormas.client.model.YesNoUnknown;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.time.Instant;
/**
 * ExposureDto
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-01-28T11:46:54.705673+01:00[Europe/Berlin]")
public class ExposureDto {
  @JsonProperty("creationDate")
  private Instant creationDate = null;

  @JsonProperty("changeDate")
  private Instant changeDate = null;

  @JsonProperty("uuid")
  private String uuid = null;

  @JsonProperty("pseudonymized")
  private Boolean pseudonymized = null;

  @JsonProperty("reportingUser")
  private UserReferenceDto reportingUser = null;

  @JsonProperty("startDate")
  private Instant startDate = null;

  @JsonProperty("endDate")
  private Instant endDate = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("exposureType")
  private ExposureType exposureType = null;

  @JsonProperty("exposureTypeDetails")
  private String exposureTypeDetails = null;

  @JsonProperty("location")
  private LocationDto location = null;

  @JsonProperty("patientExpositionRole")
  private PatientExpositionRole patientExpositionRole = null;

  @JsonProperty("typeOfPlace")
  private TypeOfPlace typeOfPlace = null;

  @JsonProperty("typeOfPlaceDetails")
  private String typeOfPlaceDetails = null;

  @JsonProperty("meansOfTransport")
  private MeansOfTransport meansOfTransport = null;

  @JsonProperty("meansOfTransportDetails")
  private String meansOfTransportDetails = null;

  @JsonProperty("connectionNumber")
  private String connectionNumber = null;

  @JsonProperty("seatNumber")
  private String seatNumber = null;

  @JsonProperty("indoors")
  private YesNoUnknown indoors = null;

  @JsonProperty("outdoors")
  private YesNoUnknown outdoors = null;

  @JsonProperty("wearingMask")
  private YesNoUnknown wearingMask = null;

  @JsonProperty("wearingPpe")
  private YesNoUnknown wearingPpe = null;

  @JsonProperty("otherProtectiveMeasures")
  private YesNoUnknown otherProtectiveMeasures = null;

  @JsonProperty("protectiveMeasuresDetails")
  private String protectiveMeasuresDetails = null;

  @JsonProperty("shortDistance")
  private YesNoUnknown shortDistance = null;

  @JsonProperty("longFaceToFaceContact")
  private YesNoUnknown longFaceToFaceContact = null;

  @JsonProperty("animalMarket")
  private YesNoUnknown animalMarket = null;

  @JsonProperty("percutaneous")
  private YesNoUnknown percutaneous = null;

  @JsonProperty("contactToBodyFluids")
  private YesNoUnknown contactToBodyFluids = null;

  @JsonProperty("handlingSamples")
  private YesNoUnknown handlingSamples = null;

  @JsonProperty("eatingRawAnimalProducts")
  private YesNoUnknown eatingRawAnimalProducts = null;

  @JsonProperty("handlingAnimals")
  private YesNoUnknown handlingAnimals = null;

  @JsonProperty("animalCondition")
  private AnimalCondition animalCondition = null;

  @JsonProperty("animalVaccinated")
  private YesNoUnknown animalVaccinated = null;

  @JsonProperty("animalContactType")
  private AnimalContactType animalContactType = null;

  @JsonProperty("animalContactTypeDetails")
  private String animalContactTypeDetails = null;

  @JsonProperty("bodyOfWater")
  private YesNoUnknown bodyOfWater = null;

  @JsonProperty("waterSource")
  private WaterSource waterSource = null;

  @JsonProperty("waterSourceDetails")
  private String waterSourceDetails = null;

  @JsonProperty("contactToCase")
  private ContactReferenceDto contactToCase = null;

  @JsonProperty("prophylaxis")
  private YesNoUnknown prophylaxis = null;

  @JsonProperty("prophylaxisDate")
  private Instant prophylaxisDate = null;

  @JsonProperty("riskArea")
  private YesNoUnknown riskArea = null;

  @JsonProperty("gatheringType")
  private GatheringType gatheringType = null;

  @JsonProperty("gatheringDetails")
  private String gatheringDetails = null;

  @JsonProperty("habitationType")
  private HabitationType habitationType = null;

  @JsonProperty("habitationDetails")
  private String habitationDetails = null;

  @JsonProperty("typeOfAnimal")
  private TypeOfAnimal typeOfAnimal = null;

  @JsonProperty("typeOfAnimalDetails")
  private String typeOfAnimalDetails = null;

  @JsonProperty("physicalContactDuringPreparation")
  private YesNoUnknown physicalContactDuringPreparation = null;

  @JsonProperty("physicalContactWithBody")
  private YesNoUnknown physicalContactWithBody = null;

  @JsonProperty("deceasedPersonIll")
  private YesNoUnknown deceasedPersonIll = null;

  @JsonProperty("deceasedPersonName")
  private String deceasedPersonName = null;

  @JsonProperty("deceasedPersonRelation")
  private String deceasedPersonRelation = null;

  public ExposureDto creationDate(Instant creationDate) {
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

  public ExposureDto changeDate(Instant changeDate) {
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

  public ExposureDto uuid(String uuid) {
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

  public ExposureDto pseudonymized(Boolean pseudonymized) {
    this.pseudonymized = pseudonymized;
    return this;
  }

   /**
   * Get pseudonymized
   * @return pseudonymized
  **/
  @Schema(description = "")
  public Boolean isPseudonymized() {
    return pseudonymized;
  }

  public void setPseudonymized(Boolean pseudonymized) {
    this.pseudonymized = pseudonymized;
  }

  public ExposureDto reportingUser(UserReferenceDto reportingUser) {
    this.reportingUser = reportingUser;
    return this;
  }

   /**
   * Get reportingUser
   * @return reportingUser
  **/
  @Schema(description = "")
  public UserReferenceDto getReportingUser() {
    return reportingUser;
  }

  public void setReportingUser(UserReferenceDto reportingUser) {
    this.reportingUser = reportingUser;
  }

  public ExposureDto startDate(Instant startDate) {
    this.startDate = startDate;
    return this;
  }

   /**
   * Get startDate
   * @return startDate
  **/
  @Schema(description = "")
  public Instant getStartDate() {
    return startDate;
  }

  public void setStartDate(Instant startDate) {
    this.startDate = startDate;
  }

  public ExposureDto endDate(Instant endDate) {
    this.endDate = endDate;
    return this;
  }

   /**
   * Get endDate
   * @return endDate
  **/
  @Schema(description = "")
  public Instant getEndDate() {
    return endDate;
  }

  public void setEndDate(Instant endDate) {
    this.endDate = endDate;
  }

  public ExposureDto description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @Schema(description = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ExposureDto exposureType(ExposureType exposureType) {
    this.exposureType = exposureType;
    return this;
  }

   /**
   * Get exposureType
   * @return exposureType
  **/
  @Schema(required = true, description = "")
  public ExposureType getExposureType() {
    return exposureType;
  }

  public void setExposureType(ExposureType exposureType) {
    this.exposureType = exposureType;
  }

  public ExposureDto exposureTypeDetails(String exposureTypeDetails) {
    this.exposureTypeDetails = exposureTypeDetails;
    return this;
  }

   /**
   * Get exposureTypeDetails
   * @return exposureTypeDetails
  **/
  @Schema(description = "")
  public String getExposureTypeDetails() {
    return exposureTypeDetails;
  }

  public void setExposureTypeDetails(String exposureTypeDetails) {
    this.exposureTypeDetails = exposureTypeDetails;
  }

  public ExposureDto location(LocationDto location) {
    this.location = location;
    return this;
  }

   /**
   * Get location
   * @return location
  **/
  @Schema(description = "")
  public LocationDto getLocation() {
    return location;
  }

  public void setLocation(LocationDto location) {
    this.location = location;
  }

  public ExposureDto patientExpositionRole(PatientExpositionRole patientExpositionRole) {
    this.patientExpositionRole = patientExpositionRole;
    return this;
  }

   /**
   * Get patientExpositionRole
   * @return patientExpositionRole
  **/
  @Schema(description = "")
  public PatientExpositionRole getPatientExpositionRole() {
    return patientExpositionRole;
  }

  public void setPatientExpositionRole(PatientExpositionRole patientExpositionRole) {
    this.patientExpositionRole = patientExpositionRole;
  }

  public ExposureDto typeOfPlace(TypeOfPlace typeOfPlace) {
    this.typeOfPlace = typeOfPlace;
    return this;
  }

   /**
   * Get typeOfPlace
   * @return typeOfPlace
  **/
  @Schema(description = "")
  public TypeOfPlace getTypeOfPlace() {
    return typeOfPlace;
  }

  public void setTypeOfPlace(TypeOfPlace typeOfPlace) {
    this.typeOfPlace = typeOfPlace;
  }

  public ExposureDto typeOfPlaceDetails(String typeOfPlaceDetails) {
    this.typeOfPlaceDetails = typeOfPlaceDetails;
    return this;
  }

   /**
   * Get typeOfPlaceDetails
   * @return typeOfPlaceDetails
  **/
  @Schema(description = "")
  public String getTypeOfPlaceDetails() {
    return typeOfPlaceDetails;
  }

  public void setTypeOfPlaceDetails(String typeOfPlaceDetails) {
    this.typeOfPlaceDetails = typeOfPlaceDetails;
  }

  public ExposureDto meansOfTransport(MeansOfTransport meansOfTransport) {
    this.meansOfTransport = meansOfTransport;
    return this;
  }

   /**
   * Get meansOfTransport
   * @return meansOfTransport
  **/
  @Schema(description = "")
  public MeansOfTransport getMeansOfTransport() {
    return meansOfTransport;
  }

  public void setMeansOfTransport(MeansOfTransport meansOfTransport) {
    this.meansOfTransport = meansOfTransport;
  }

  public ExposureDto meansOfTransportDetails(String meansOfTransportDetails) {
    this.meansOfTransportDetails = meansOfTransportDetails;
    return this;
  }

   /**
   * Get meansOfTransportDetails
   * @return meansOfTransportDetails
  **/
  @Schema(description = "")
  public String getMeansOfTransportDetails() {
    return meansOfTransportDetails;
  }

  public void setMeansOfTransportDetails(String meansOfTransportDetails) {
    this.meansOfTransportDetails = meansOfTransportDetails;
  }

  public ExposureDto connectionNumber(String connectionNumber) {
    this.connectionNumber = connectionNumber;
    return this;
  }

   /**
   * Get connectionNumber
   * @return connectionNumber
  **/
  @Schema(description = "")
  public String getConnectionNumber() {
    return connectionNumber;
  }

  public void setConnectionNumber(String connectionNumber) {
    this.connectionNumber = connectionNumber;
  }

  public ExposureDto seatNumber(String seatNumber) {
    this.seatNumber = seatNumber;
    return this;
  }

   /**
   * Get seatNumber
   * @return seatNumber
  **/
  @Schema(description = "")
  public String getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(String seatNumber) {
    this.seatNumber = seatNumber;
  }

  public ExposureDto indoors(YesNoUnknown indoors) {
    this.indoors = indoors;
    return this;
  }

   /**
   * Get indoors
   * @return indoors
  **/
  @Schema(description = "")
  public YesNoUnknown getIndoors() {
    return indoors;
  }

  public void setIndoors(YesNoUnknown indoors) {
    this.indoors = indoors;
  }

  public ExposureDto outdoors(YesNoUnknown outdoors) {
    this.outdoors = outdoors;
    return this;
  }

   /**
   * Get outdoors
   * @return outdoors
  **/
  @Schema(description = "")
  public YesNoUnknown getOutdoors() {
    return outdoors;
  }

  public void setOutdoors(YesNoUnknown outdoors) {
    this.outdoors = outdoors;
  }

  public ExposureDto wearingMask(YesNoUnknown wearingMask) {
    this.wearingMask = wearingMask;
    return this;
  }

   /**
   * Get wearingMask
   * @return wearingMask
  **/
  @Schema(description = "")
  public YesNoUnknown getWearingMask() {
    return wearingMask;
  }

  public void setWearingMask(YesNoUnknown wearingMask) {
    this.wearingMask = wearingMask;
  }

  public ExposureDto wearingPpe(YesNoUnknown wearingPpe) {
    this.wearingPpe = wearingPpe;
    return this;
  }

   /**
   * Get wearingPpe
   * @return wearingPpe
  **/
  @Schema(description = "")
  public YesNoUnknown getWearingPpe() {
    return wearingPpe;
  }

  public void setWearingPpe(YesNoUnknown wearingPpe) {
    this.wearingPpe = wearingPpe;
  }

  public ExposureDto otherProtectiveMeasures(YesNoUnknown otherProtectiveMeasures) {
    this.otherProtectiveMeasures = otherProtectiveMeasures;
    return this;
  }

   /**
   * Get otherProtectiveMeasures
   * @return otherProtectiveMeasures
  **/
  @Schema(description = "")
  public YesNoUnknown getOtherProtectiveMeasures() {
    return otherProtectiveMeasures;
  }

  public void setOtherProtectiveMeasures(YesNoUnknown otherProtectiveMeasures) {
    this.otherProtectiveMeasures = otherProtectiveMeasures;
  }

  public ExposureDto protectiveMeasuresDetails(String protectiveMeasuresDetails) {
    this.protectiveMeasuresDetails = protectiveMeasuresDetails;
    return this;
  }

   /**
   * Get protectiveMeasuresDetails
   * @return protectiveMeasuresDetails
  **/
  @Schema(description = "")
  public String getProtectiveMeasuresDetails() {
    return protectiveMeasuresDetails;
  }

  public void setProtectiveMeasuresDetails(String protectiveMeasuresDetails) {
    this.protectiveMeasuresDetails = protectiveMeasuresDetails;
  }

  public ExposureDto shortDistance(YesNoUnknown shortDistance) {
    this.shortDistance = shortDistance;
    return this;
  }

   /**
   * Get shortDistance
   * @return shortDistance
  **/
  @Schema(description = "")
  public YesNoUnknown getShortDistance() {
    return shortDistance;
  }

  public void setShortDistance(YesNoUnknown shortDistance) {
    this.shortDistance = shortDistance;
  }

  public ExposureDto longFaceToFaceContact(YesNoUnknown longFaceToFaceContact) {
    this.longFaceToFaceContact = longFaceToFaceContact;
    return this;
  }

   /**
   * Get longFaceToFaceContact
   * @return longFaceToFaceContact
  **/
  @Schema(description = "")
  public YesNoUnknown getLongFaceToFaceContact() {
    return longFaceToFaceContact;
  }

  public void setLongFaceToFaceContact(YesNoUnknown longFaceToFaceContact) {
    this.longFaceToFaceContact = longFaceToFaceContact;
  }

  public ExposureDto animalMarket(YesNoUnknown animalMarket) {
    this.animalMarket = animalMarket;
    return this;
  }

   /**
   * Get animalMarket
   * @return animalMarket
  **/
  @Schema(description = "")
  public YesNoUnknown getAnimalMarket() {
    return animalMarket;
  }

  public void setAnimalMarket(YesNoUnknown animalMarket) {
    this.animalMarket = animalMarket;
  }

  public ExposureDto percutaneous(YesNoUnknown percutaneous) {
    this.percutaneous = percutaneous;
    return this;
  }

   /**
   * Get percutaneous
   * @return percutaneous
  **/
  @Schema(description = "")
  public YesNoUnknown getPercutaneous() {
    return percutaneous;
  }

  public void setPercutaneous(YesNoUnknown percutaneous) {
    this.percutaneous = percutaneous;
  }

  public ExposureDto contactToBodyFluids(YesNoUnknown contactToBodyFluids) {
    this.contactToBodyFluids = contactToBodyFluids;
    return this;
  }

   /**
   * Get contactToBodyFluids
   * @return contactToBodyFluids
  **/
  @Schema(description = "")
  public YesNoUnknown getContactToBodyFluids() {
    return contactToBodyFluids;
  }

  public void setContactToBodyFluids(YesNoUnknown contactToBodyFluids) {
    this.contactToBodyFluids = contactToBodyFluids;
  }

  public ExposureDto handlingSamples(YesNoUnknown handlingSamples) {
    this.handlingSamples = handlingSamples;
    return this;
  }

   /**
   * Get handlingSamples
   * @return handlingSamples
  **/
  @Schema(description = "")
  public YesNoUnknown getHandlingSamples() {
    return handlingSamples;
  }

  public void setHandlingSamples(YesNoUnknown handlingSamples) {
    this.handlingSamples = handlingSamples;
  }

  public ExposureDto eatingRawAnimalProducts(YesNoUnknown eatingRawAnimalProducts) {
    this.eatingRawAnimalProducts = eatingRawAnimalProducts;
    return this;
  }

   /**
   * Get eatingRawAnimalProducts
   * @return eatingRawAnimalProducts
  **/
  @Schema(description = "")
  public YesNoUnknown getEatingRawAnimalProducts() {
    return eatingRawAnimalProducts;
  }

  public void setEatingRawAnimalProducts(YesNoUnknown eatingRawAnimalProducts) {
    this.eatingRawAnimalProducts = eatingRawAnimalProducts;
  }

  public ExposureDto handlingAnimals(YesNoUnknown handlingAnimals) {
    this.handlingAnimals = handlingAnimals;
    return this;
  }

   /**
   * Get handlingAnimals
   * @return handlingAnimals
  **/
  @Schema(description = "")
  public YesNoUnknown getHandlingAnimals() {
    return handlingAnimals;
  }

  public void setHandlingAnimals(YesNoUnknown handlingAnimals) {
    this.handlingAnimals = handlingAnimals;
  }

  public ExposureDto animalCondition(AnimalCondition animalCondition) {
    this.animalCondition = animalCondition;
    return this;
  }

   /**
   * Get animalCondition
   * @return animalCondition
  **/
  @Schema(description = "")
  public AnimalCondition getAnimalCondition() {
    return animalCondition;
  }

  public void setAnimalCondition(AnimalCondition animalCondition) {
    this.animalCondition = animalCondition;
  }

  public ExposureDto animalVaccinated(YesNoUnknown animalVaccinated) {
    this.animalVaccinated = animalVaccinated;
    return this;
  }

   /**
   * Get animalVaccinated
   * @return animalVaccinated
  **/
  @Schema(description = "")
  public YesNoUnknown getAnimalVaccinated() {
    return animalVaccinated;
  }

  public void setAnimalVaccinated(YesNoUnknown animalVaccinated) {
    this.animalVaccinated = animalVaccinated;
  }

  public ExposureDto animalContactType(AnimalContactType animalContactType) {
    this.animalContactType = animalContactType;
    return this;
  }

   /**
   * Get animalContactType
   * @return animalContactType
  **/
  @Schema(description = "")
  public AnimalContactType getAnimalContactType() {
    return animalContactType;
  }

  public void setAnimalContactType(AnimalContactType animalContactType) {
    this.animalContactType = animalContactType;
  }

  public ExposureDto animalContactTypeDetails(String animalContactTypeDetails) {
    this.animalContactTypeDetails = animalContactTypeDetails;
    return this;
  }

   /**
   * Get animalContactTypeDetails
   * @return animalContactTypeDetails
  **/
  @Schema(description = "")
  public String getAnimalContactTypeDetails() {
    return animalContactTypeDetails;
  }

  public void setAnimalContactTypeDetails(String animalContactTypeDetails) {
    this.animalContactTypeDetails = animalContactTypeDetails;
  }

  public ExposureDto bodyOfWater(YesNoUnknown bodyOfWater) {
    this.bodyOfWater = bodyOfWater;
    return this;
  }

   /**
   * Get bodyOfWater
   * @return bodyOfWater
  **/
  @Schema(description = "")
  public YesNoUnknown getBodyOfWater() {
    return bodyOfWater;
  }

  public void setBodyOfWater(YesNoUnknown bodyOfWater) {
    this.bodyOfWater = bodyOfWater;
  }

  public ExposureDto waterSource(WaterSource waterSource) {
    this.waterSource = waterSource;
    return this;
  }

   /**
   * Get waterSource
   * @return waterSource
  **/
  @Schema(description = "")
  public WaterSource getWaterSource() {
    return waterSource;
  }

  public void setWaterSource(WaterSource waterSource) {
    this.waterSource = waterSource;
  }

  public ExposureDto waterSourceDetails(String waterSourceDetails) {
    this.waterSourceDetails = waterSourceDetails;
    return this;
  }

   /**
   * Get waterSourceDetails
   * @return waterSourceDetails
  **/
  @Schema(description = "")
  public String getWaterSourceDetails() {
    return waterSourceDetails;
  }

  public void setWaterSourceDetails(String waterSourceDetails) {
    this.waterSourceDetails = waterSourceDetails;
  }

  public ExposureDto contactToCase(ContactReferenceDto contactToCase) {
    this.contactToCase = contactToCase;
    return this;
  }

   /**
   * Get contactToCase
   * @return contactToCase
  **/
  @Schema(description = "")
  public ContactReferenceDto getContactToCase() {
    return contactToCase;
  }

  public void setContactToCase(ContactReferenceDto contactToCase) {
    this.contactToCase = contactToCase;
  }

  public ExposureDto prophylaxis(YesNoUnknown prophylaxis) {
    this.prophylaxis = prophylaxis;
    return this;
  }

   /**
   * Get prophylaxis
   * @return prophylaxis
  **/
  @Schema(description = "")
  public YesNoUnknown getProphylaxis() {
    return prophylaxis;
  }

  public void setProphylaxis(YesNoUnknown prophylaxis) {
    this.prophylaxis = prophylaxis;
  }

  public ExposureDto prophylaxisDate(Instant prophylaxisDate) {
    this.prophylaxisDate = prophylaxisDate;
    return this;
  }

   /**
   * Get prophylaxisDate
   * @return prophylaxisDate
  **/
  @Schema(description = "")
  public Instant getProphylaxisDate() {
    return prophylaxisDate;
  }

  public void setProphylaxisDate(Instant prophylaxisDate) {
    this.prophylaxisDate = prophylaxisDate;
  }

  public ExposureDto riskArea(YesNoUnknown riskArea) {
    this.riskArea = riskArea;
    return this;
  }

   /**
   * Get riskArea
   * @return riskArea
  **/
  @Schema(description = "")
  public YesNoUnknown getRiskArea() {
    return riskArea;
  }

  public void setRiskArea(YesNoUnknown riskArea) {
    this.riskArea = riskArea;
  }

  public ExposureDto gatheringType(GatheringType gatheringType) {
    this.gatheringType = gatheringType;
    return this;
  }

   /**
   * Get gatheringType
   * @return gatheringType
  **/
  @Schema(description = "")
  public GatheringType getGatheringType() {
    return gatheringType;
  }

  public void setGatheringType(GatheringType gatheringType) {
    this.gatheringType = gatheringType;
  }

  public ExposureDto gatheringDetails(String gatheringDetails) {
    this.gatheringDetails = gatheringDetails;
    return this;
  }

   /**
   * Get gatheringDetails
   * @return gatheringDetails
  **/
  @Schema(description = "")
  public String getGatheringDetails() {
    return gatheringDetails;
  }

  public void setGatheringDetails(String gatheringDetails) {
    this.gatheringDetails = gatheringDetails;
  }

  public ExposureDto habitationType(HabitationType habitationType) {
    this.habitationType = habitationType;
    return this;
  }

   /**
   * Get habitationType
   * @return habitationType
  **/
  @Schema(description = "")
  public HabitationType getHabitationType() {
    return habitationType;
  }

  public void setHabitationType(HabitationType habitationType) {
    this.habitationType = habitationType;
  }

  public ExposureDto habitationDetails(String habitationDetails) {
    this.habitationDetails = habitationDetails;
    return this;
  }

   /**
   * Get habitationDetails
   * @return habitationDetails
  **/
  @Schema(description = "")
  public String getHabitationDetails() {
    return habitationDetails;
  }

  public void setHabitationDetails(String habitationDetails) {
    this.habitationDetails = habitationDetails;
  }

  public ExposureDto typeOfAnimal(TypeOfAnimal typeOfAnimal) {
    this.typeOfAnimal = typeOfAnimal;
    return this;
  }

   /**
   * Get typeOfAnimal
   * @return typeOfAnimal
  **/
  @Schema(description = "")
  public TypeOfAnimal getTypeOfAnimal() {
    return typeOfAnimal;
  }

  public void setTypeOfAnimal(TypeOfAnimal typeOfAnimal) {
    this.typeOfAnimal = typeOfAnimal;
  }

  public ExposureDto typeOfAnimalDetails(String typeOfAnimalDetails) {
    this.typeOfAnimalDetails = typeOfAnimalDetails;
    return this;
  }

   /**
   * Get typeOfAnimalDetails
   * @return typeOfAnimalDetails
  **/
  @Schema(description = "")
  public String getTypeOfAnimalDetails() {
    return typeOfAnimalDetails;
  }

  public void setTypeOfAnimalDetails(String typeOfAnimalDetails) {
    this.typeOfAnimalDetails = typeOfAnimalDetails;
  }

  public ExposureDto physicalContactDuringPreparation(YesNoUnknown physicalContactDuringPreparation) {
    this.physicalContactDuringPreparation = physicalContactDuringPreparation;
    return this;
  }

   /**
   * Get physicalContactDuringPreparation
   * @return physicalContactDuringPreparation
  **/
  @Schema(description = "")
  public YesNoUnknown getPhysicalContactDuringPreparation() {
    return physicalContactDuringPreparation;
  }

  public void setPhysicalContactDuringPreparation(YesNoUnknown physicalContactDuringPreparation) {
    this.physicalContactDuringPreparation = physicalContactDuringPreparation;
  }

  public ExposureDto physicalContactWithBody(YesNoUnknown physicalContactWithBody) {
    this.physicalContactWithBody = physicalContactWithBody;
    return this;
  }

   /**
   * Get physicalContactWithBody
   * @return physicalContactWithBody
  **/
  @Schema(description = "")
  public YesNoUnknown getPhysicalContactWithBody() {
    return physicalContactWithBody;
  }

  public void setPhysicalContactWithBody(YesNoUnknown physicalContactWithBody) {
    this.physicalContactWithBody = physicalContactWithBody;
  }

  public ExposureDto deceasedPersonIll(YesNoUnknown deceasedPersonIll) {
    this.deceasedPersonIll = deceasedPersonIll;
    return this;
  }

   /**
   * Get deceasedPersonIll
   * @return deceasedPersonIll
  **/
  @Schema(description = "")
  public YesNoUnknown getDeceasedPersonIll() {
    return deceasedPersonIll;
  }

  public void setDeceasedPersonIll(YesNoUnknown deceasedPersonIll) {
    this.deceasedPersonIll = deceasedPersonIll;
  }

  public ExposureDto deceasedPersonName(String deceasedPersonName) {
    this.deceasedPersonName = deceasedPersonName;
    return this;
  }

   /**
   * Get deceasedPersonName
   * @return deceasedPersonName
  **/
  @Schema(description = "")
  public String getDeceasedPersonName() {
    return deceasedPersonName;
  }

  public void setDeceasedPersonName(String deceasedPersonName) {
    this.deceasedPersonName = deceasedPersonName;
  }

  public ExposureDto deceasedPersonRelation(String deceasedPersonRelation) {
    this.deceasedPersonRelation = deceasedPersonRelation;
    return this;
  }

   /**
   * Get deceasedPersonRelation
   * @return deceasedPersonRelation
  **/
  @Schema(description = "")
  public String getDeceasedPersonRelation() {
    return deceasedPersonRelation;
  }

  public void setDeceasedPersonRelation(String deceasedPersonRelation) {
    this.deceasedPersonRelation = deceasedPersonRelation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExposureDto exposureDto = (ExposureDto) o;
    return Objects.equals(this.creationDate, exposureDto.creationDate) &&
        Objects.equals(this.changeDate, exposureDto.changeDate) &&
        Objects.equals(this.uuid, exposureDto.uuid) &&
        Objects.equals(this.pseudonymized, exposureDto.pseudonymized) &&
        Objects.equals(this.reportingUser, exposureDto.reportingUser) &&
        Objects.equals(this.startDate, exposureDto.startDate) &&
        Objects.equals(this.endDate, exposureDto.endDate) &&
        Objects.equals(this.description, exposureDto.description) &&
        Objects.equals(this.exposureType, exposureDto.exposureType) &&
        Objects.equals(this.exposureTypeDetails, exposureDto.exposureTypeDetails) &&
        Objects.equals(this.location, exposureDto.location) &&
        Objects.equals(this.patientExpositionRole, exposureDto.patientExpositionRole) &&
        Objects.equals(this.typeOfPlace, exposureDto.typeOfPlace) &&
        Objects.equals(this.typeOfPlaceDetails, exposureDto.typeOfPlaceDetails) &&
        Objects.equals(this.meansOfTransport, exposureDto.meansOfTransport) &&
        Objects.equals(this.meansOfTransportDetails, exposureDto.meansOfTransportDetails) &&
        Objects.equals(this.connectionNumber, exposureDto.connectionNumber) &&
        Objects.equals(this.seatNumber, exposureDto.seatNumber) &&
        Objects.equals(this.indoors, exposureDto.indoors) &&
        Objects.equals(this.outdoors, exposureDto.outdoors) &&
        Objects.equals(this.wearingMask, exposureDto.wearingMask) &&
        Objects.equals(this.wearingPpe, exposureDto.wearingPpe) &&
        Objects.equals(this.otherProtectiveMeasures, exposureDto.otherProtectiveMeasures) &&
        Objects.equals(this.protectiveMeasuresDetails, exposureDto.protectiveMeasuresDetails) &&
        Objects.equals(this.shortDistance, exposureDto.shortDistance) &&
        Objects.equals(this.longFaceToFaceContact, exposureDto.longFaceToFaceContact) &&
        Objects.equals(this.animalMarket, exposureDto.animalMarket) &&
        Objects.equals(this.percutaneous, exposureDto.percutaneous) &&
        Objects.equals(this.contactToBodyFluids, exposureDto.contactToBodyFluids) &&
        Objects.equals(this.handlingSamples, exposureDto.handlingSamples) &&
        Objects.equals(this.eatingRawAnimalProducts, exposureDto.eatingRawAnimalProducts) &&
        Objects.equals(this.handlingAnimals, exposureDto.handlingAnimals) &&
        Objects.equals(this.animalCondition, exposureDto.animalCondition) &&
        Objects.equals(this.animalVaccinated, exposureDto.animalVaccinated) &&
        Objects.equals(this.animalContactType, exposureDto.animalContactType) &&
        Objects.equals(this.animalContactTypeDetails, exposureDto.animalContactTypeDetails) &&
        Objects.equals(this.bodyOfWater, exposureDto.bodyOfWater) &&
        Objects.equals(this.waterSource, exposureDto.waterSource) &&
        Objects.equals(this.waterSourceDetails, exposureDto.waterSourceDetails) &&
        Objects.equals(this.contactToCase, exposureDto.contactToCase) &&
        Objects.equals(this.prophylaxis, exposureDto.prophylaxis) &&
        Objects.equals(this.prophylaxisDate, exposureDto.prophylaxisDate) &&
        Objects.equals(this.riskArea, exposureDto.riskArea) &&
        Objects.equals(this.gatheringType, exposureDto.gatheringType) &&
        Objects.equals(this.gatheringDetails, exposureDto.gatheringDetails) &&
        Objects.equals(this.habitationType, exposureDto.habitationType) &&
        Objects.equals(this.habitationDetails, exposureDto.habitationDetails) &&
        Objects.equals(this.typeOfAnimal, exposureDto.typeOfAnimal) &&
        Objects.equals(this.typeOfAnimalDetails, exposureDto.typeOfAnimalDetails) &&
        Objects.equals(this.physicalContactDuringPreparation, exposureDto.physicalContactDuringPreparation) &&
        Objects.equals(this.physicalContactWithBody, exposureDto.physicalContactWithBody) &&
        Objects.equals(this.deceasedPersonIll, exposureDto.deceasedPersonIll) &&
        Objects.equals(this.deceasedPersonName, exposureDto.deceasedPersonName) &&
        Objects.equals(this.deceasedPersonRelation, exposureDto.deceasedPersonRelation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(creationDate, changeDate, uuid, pseudonymized, reportingUser, startDate, endDate, description, exposureType, exposureTypeDetails, location, patientExpositionRole, typeOfPlace, typeOfPlaceDetails, meansOfTransport, meansOfTransportDetails, connectionNumber, seatNumber, indoors, outdoors, wearingMask, wearingPpe, otherProtectiveMeasures, protectiveMeasuresDetails, shortDistance, longFaceToFaceContact, animalMarket, percutaneous, contactToBodyFluids, handlingSamples, eatingRawAnimalProducts, handlingAnimals, animalCondition, animalVaccinated, animalContactType, animalContactTypeDetails, bodyOfWater, waterSource, waterSourceDetails, contactToCase, prophylaxis, prophylaxisDate, riskArea, gatheringType, gatheringDetails, habitationType, habitationDetails, typeOfAnimal, typeOfAnimalDetails, physicalContactDuringPreparation, physicalContactWithBody, deceasedPersonIll, deceasedPersonName, deceasedPersonRelation);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExposureDto {\n");
    
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    changeDate: ").append(toIndentedString(changeDate)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    pseudonymized: ").append(toIndentedString(pseudonymized)).append("\n");
    sb.append("    reportingUser: ").append(toIndentedString(reportingUser)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    exposureType: ").append(toIndentedString(exposureType)).append("\n");
    sb.append("    exposureTypeDetails: ").append(toIndentedString(exposureTypeDetails)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    patientExpositionRole: ").append(toIndentedString(patientExpositionRole)).append("\n");
    sb.append("    typeOfPlace: ").append(toIndentedString(typeOfPlace)).append("\n");
    sb.append("    typeOfPlaceDetails: ").append(toIndentedString(typeOfPlaceDetails)).append("\n");
    sb.append("    meansOfTransport: ").append(toIndentedString(meansOfTransport)).append("\n");
    sb.append("    meansOfTransportDetails: ").append(toIndentedString(meansOfTransportDetails)).append("\n");
    sb.append("    connectionNumber: ").append(toIndentedString(connectionNumber)).append("\n");
    sb.append("    seatNumber: ").append(toIndentedString(seatNumber)).append("\n");
    sb.append("    indoors: ").append(toIndentedString(indoors)).append("\n");
    sb.append("    outdoors: ").append(toIndentedString(outdoors)).append("\n");
    sb.append("    wearingMask: ").append(toIndentedString(wearingMask)).append("\n");
    sb.append("    wearingPpe: ").append(toIndentedString(wearingPpe)).append("\n");
    sb.append("    otherProtectiveMeasures: ").append(toIndentedString(otherProtectiveMeasures)).append("\n");
    sb.append("    protectiveMeasuresDetails: ").append(toIndentedString(protectiveMeasuresDetails)).append("\n");
    sb.append("    shortDistance: ").append(toIndentedString(shortDistance)).append("\n");
    sb.append("    longFaceToFaceContact: ").append(toIndentedString(longFaceToFaceContact)).append("\n");
    sb.append("    animalMarket: ").append(toIndentedString(animalMarket)).append("\n");
    sb.append("    percutaneous: ").append(toIndentedString(percutaneous)).append("\n");
    sb.append("    contactToBodyFluids: ").append(toIndentedString(contactToBodyFluids)).append("\n");
    sb.append("    handlingSamples: ").append(toIndentedString(handlingSamples)).append("\n");
    sb.append("    eatingRawAnimalProducts: ").append(toIndentedString(eatingRawAnimalProducts)).append("\n");
    sb.append("    handlingAnimals: ").append(toIndentedString(handlingAnimals)).append("\n");
    sb.append("    animalCondition: ").append(toIndentedString(animalCondition)).append("\n");
    sb.append("    animalVaccinated: ").append(toIndentedString(animalVaccinated)).append("\n");
    sb.append("    animalContactType: ").append(toIndentedString(animalContactType)).append("\n");
    sb.append("    animalContactTypeDetails: ").append(toIndentedString(animalContactTypeDetails)).append("\n");
    sb.append("    bodyOfWater: ").append(toIndentedString(bodyOfWater)).append("\n");
    sb.append("    waterSource: ").append(toIndentedString(waterSource)).append("\n");
    sb.append("    waterSourceDetails: ").append(toIndentedString(waterSourceDetails)).append("\n");
    sb.append("    contactToCase: ").append(toIndentedString(contactToCase)).append("\n");
    sb.append("    prophylaxis: ").append(toIndentedString(prophylaxis)).append("\n");
    sb.append("    prophylaxisDate: ").append(toIndentedString(prophylaxisDate)).append("\n");
    sb.append("    riskArea: ").append(toIndentedString(riskArea)).append("\n");
    sb.append("    gatheringType: ").append(toIndentedString(gatheringType)).append("\n");
    sb.append("    gatheringDetails: ").append(toIndentedString(gatheringDetails)).append("\n");
    sb.append("    habitationType: ").append(toIndentedString(habitationType)).append("\n");
    sb.append("    habitationDetails: ").append(toIndentedString(habitationDetails)).append("\n");
    sb.append("    typeOfAnimal: ").append(toIndentedString(typeOfAnimal)).append("\n");
    sb.append("    typeOfAnimalDetails: ").append(toIndentedString(typeOfAnimalDetails)).append("\n");
    sb.append("    physicalContactDuringPreparation: ").append(toIndentedString(physicalContactDuringPreparation)).append("\n");
    sb.append("    physicalContactWithBody: ").append(toIndentedString(physicalContactWithBody)).append("\n");
    sb.append("    deceasedPersonIll: ").append(toIndentedString(deceasedPersonIll)).append("\n");
    sb.append("    deceasedPersonName: ").append(toIndentedString(deceasedPersonName)).append("\n");
    sb.append("    deceasedPersonRelation: ").append(toIndentedString(deceasedPersonRelation)).append("\n");
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
