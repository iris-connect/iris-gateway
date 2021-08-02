package iris.backend_service.locations.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import iris.backend_service.alerts.AlertService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

/**
 * This test runs only with mvn successfully, because the logback must exclude during test run!
 */
@ExtendWith(MockitoExtension.class)
class ValidationHelperTest {

	@Mock
	Logger logger;
	@Mock
	AlertService alerts;

	@InjectMocks
	ValidationHelper sut;

	@ParameterizedTest
	@ValueSource(strings = { "a@b.de", "rick4711@yahoo.com", "r@sub.main.org", "r@foo-bar.org" })
	void checkValidEmailAddresses(String email) {
		assertTrue(ValidationHelper.isValidAndNotNullEmail(email));

	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "a@b", "a@b.de.", "rick4711yahoo.com", "@online.org" })
	void checkInvalidEmailAddresses(String email) {
		assertFalse(ValidationHelper.isValidAndNotNullEmail(email));
	}

	@ParameterizedTest
	@ValueSource(strings = { "0123456789", "+4912345678", "+3412345678", "0172 111567" }) // other variants:
																																												// "0172/111567", "0222 6578-22"
	void checkValidPhoneNumbers(String phone) {
		assertTrue(ValidationHelper.isValidAndNotNullPhoneNumber(phone));
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "0123A56789", "01234%678", "0815 47+11", "0123456*89", "0", "12345678" })
	void checkInvalidPhoneNumbers(String phone) {
		assertFalse(ValidationHelper.isValidAndNotNullEmail(phone));
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "  " })
	void checkPossibleAttackForRequiredValueTrueForNull(String input) {

		assertTrue(sut.isPossibleAttackForRequiredValue(input, "Field", false, "client"));

		verify(logger).warn(startsWith("Die übergebenen Daten dürfen nicht leer sein"), any(Object.class));
		verifyNoMoreInteractions(logger, alerts);
	}

	@ParameterizedTest
	@ValueSource(strings = { "$Test", "ﬁTest", "[", "¢", "¶", "", "æ", "Æ", "œ", "Œ", "@", "•Test", "°Test", "<SCRIPT",
			"SELECT iuaeuia uiae FROM", "INSERT    INTO" })
	void checkPossibleAttackTrue(String input) {

		assertTrue(sut.isPossibleAttackForRequiredValue(input, "Field", false, "client"));

		verify(logger).warn(startsWith("Die übergebenen Eingabedaten sind nicht erlaubt"), eq("Field"), eq(input));
		verify(alerts).createAlertMessage(any(), contains(input));

		assertTrue(sut.isPossibleAttack(input, "Field", false, "client"));

		verify(logger, times(2)).warn(startsWith("Die übergebenen Eingabedaten sind nicht erlaubt"), eq("Field"),
				eq(input));
		verify(alerts, times(2)).createAlertMessage(any(), contains(input));
		verifyNoMoreInteractions(logger, alerts);
	}

	@ParameterizedTest
	@ValueSource(strings = { "Test", "Haus", "iae dntrn dn" })
	void checkPossibleAttackFalse(String input) {

		assertFalse(sut.isPossibleAttackForRequiredValue(input, "Field", false, "client"));
		assertFalse(sut.isPossibleAttack(input, "Field", false, "client"));

		verifyNoInteractions(logger, alerts);
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "  " })
	void checkPossibleAttackFalseForNull(String input) {

		assertFalse(sut.isPossibleAttack(input, "Field", false, "client"));

		verifyNoInteractions(logger, alerts);
	}

	@ParameterizedTest
	@ValueSource(strings = { "$Testwert", "ﬁTestwert", "[Testwert", "¢Testwert", "¶Testwert", "Testwert", "æTestwert",
			"ÆTestwert", "œTestwert", "ŒTestwert", "@Testwert", "•Testwert", "°Testwert", "<SCRIPTTestwert",
			"SELECT iuaeuia uiae FROMTestwert", "INSERT    INTOTestwert" })
	void checkObfuscate(String input) {

		assertTrue(sut.isPossibleAttack(input, "Field", true, "client"));

		verify(logger).warn(startsWith("Die übergebenen Eingabedaten sind nicht erlaubt"), eq("Field"),
				endsWith("Tes*****"));
		verify(alerts).createAlertMessage(any(), contains("Tes*****"));
		verifyNoMoreInteractions(logger, alerts);
	}
}
