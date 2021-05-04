package iris.public_server.data_submission.web;

import static iris.public_server.data_request.DataRequestDataInitializer.*;
import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import iris.public_server.IrisWebIntegrationTest;
import iris.public_server.core.Feature;
import iris.public_server.data_request.DataRequest.DataRequestIdentifier;
import iris.public_server.data_submission.model.DataSubmission;
import iris.public_server.data_submission.repository.DataSubmissionRepository;
import iris.public_server.data_submission.web.dto.ContactsEventsSubmissionDto;
import iris.public_server.data_submission.web.dto.DataSubmissionDto;
import iris.public_server.data_submission.web.dto.GuestsSubmissionDto;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@IrisWebIntegrationTest
@RequiredArgsConstructor
class DataSubmissionApiControllerIntegrationTests {

	private static final String DATA = Base64.encodeBase64String("EncryptedData".getBytes());

	private final MockMvc mvc;
	private final ObjectMapper mapper;

	@MockBean
	private DataSubmissionRepository dataSubmissions;

	@Captor
	ArgumentCaptor<DataSubmission> captor;

	@Test
	void postContactsEventsSubmission() throws Exception {

		when(dataSubmissions.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

		performPostSubmission(createContactSubmission(), "/data-submissions/{code}/contacts_events", REQ_ID_3,
				status().isAccepted());

		Mockito.verify(dataSubmissions).save(captor.capture());
		var value = captor.getValue();
		assertThat(value.getEncryptedData()).isEqualTo(DATA);
		assertThat(value.getFeature()).isEqualTo(Feature.Contacts_Events);
		assertThat(value.getRequestId()).isEqualTo(REQ_ID_3);
	}

	@Test
	void postGuestsSubmission() throws Exception {

		when(dataSubmissions.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

		performPostSubmission(createGuestSubmission(), "/data-submissions/{code}/guests", REQ_ID_2, status().isAccepted());

		Mockito.verify(dataSubmissions).save(captor.capture());
		var value = captor.getValue();
		assertThat(value.getEncryptedData()).isEqualTo(DATA);
		assertThat(value.getFeature()).isEqualTo(Feature.Guests);
		assertThat(value.getRequestId()).isEqualTo(REQ_ID_2);
	}

	@Test
	void postSubmission_missingRequestId() throws Exception {

		performPostSubmission(createContactSubmission(), "/data-submissions/{code}/contacts_events",
				DataRequestIdentifier.of(UUID.randomUUID()), status().isNotFound());

		performPostSubmission(createGuestSubmission(), "/data-submissions/{code}/guests",
				DataRequestIdentifier.of(UUID.randomUUID()), status().isNotFound());
	}

	@Test
	void postSubmission_wrongFeature() throws Exception {

		var result = performPostSubmission(createContactSubmission(), "/data-submissions/{code}/contacts_events",
				REQ_ID_2, status().isBadRequest());

		var response = result.andReturn().getResponse().getContentAsString();
		var document = JsonPath.parse(response);

		assertThat(document.read("$", String.class)).isNotNull();

		result = performPostSubmission(createGuestSubmission(), "/data-submissions/{code}/guests",
				REQ_ID_3, status().isBadRequest());

		response = result.andReturn().getResponse().getContentAsString();
		document = JsonPath.parse(response);

		assertThat(document.read("$", String.class)).isNotNull();
	}

	@Test
	void postSubmission_dataNotBase64() throws Exception {

		var dto2 = new ContactsEventsSubmissionDto();
		dto2.setEncryptedData("EncryptedData");
		fillDto(dto2);
		performPostSubmission(dto2, "/data-submissions/{code}/contacts_events",
				REQ_ID_3, status().isBadRequest());

		var dto = new GuestsSubmissionDto();
		dto.setEncryptedData("EncryptedData");
		fillDto(dto);
		performPostSubmission(dto, "/data-submissions/{code}/guests",
				REQ_ID_2, status().isBadRequest());
	}

	@Test
	void postSubmission_closedReq() throws Exception {

		var result = performPostSubmission(createContactSubmission(), "/data-submissions/{code}/contacts_events",
				REQ_ID_4, status().isBadRequest());

		var response = result.andReturn().getResponse().getContentAsString();
		var document = JsonPath.parse(response);

		assertThat(document.read("$", String.class)).isNotNull();
	}

	private ContactsEventsSubmissionDto createContactSubmission() {
		var dto = new ContactsEventsSubmissionDto();
		dto.setEncryptedData(DATA);
		fillDto(dto);
		return dto;
	}

	private GuestsSubmissionDto createGuestSubmission() {
		var dto = new GuestsSubmissionDto();
		dto.setEncryptedData(DATA);
		fillDto(dto);
		return dto;
	}

	private void fillDto(DataSubmissionDto dto) {
		dto.setKeyReference("KeyReference");
		dto.setSecret("Secret");
	}

	private ResultActions performPostSubmission(DataSubmissionDto dto, String uri, DataRequestIdentifier id,
			ResultMatcher resultMatcher)
			throws Exception, JsonProcessingException {
		return mvc.perform(
				post(uri, id.toString())
						.contentType(new MediaType(APPLICATION_JSON, UTF_8))
						.content(mapper.writeValueAsString(dto)))
				.andExpect(resultMatcher);
	}
}
