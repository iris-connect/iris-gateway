package iris.backend_service.locations.utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import iris.backend_service.MemoryAppender;
import iris.backend_service.alerts.AlertService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

/**
 * This test runs only with mvn successfully, because the logback must exclude during test run!
 */
@ExtendWith(MockitoExtension.class)
class ValidationHelperTest {

	@Mock
	AlertService alerts;

	@InjectMocks
	ValidationHelper sut;

	private MemoryAppender memoryAppender;

	@BeforeEach
	public void setup() {
		Logger logger = (Logger) LoggerFactory.getLogger(ValidationHelper.class);
		memoryAppender = new MemoryAppender();
		memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
		logger.setLevel(Level.DEBUG);
		logger.addAppender(memoryAppender);
		memoryAppender.start();
	}

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
	@ValueSource(strings = { "0123456789", "+49 12345 67833", "+3412345678", "0172 111567" }) // other variants:
	// "0172/111567", "0222 6578-22"
	void checkValidPhoneNumbers(String phone) {
		assertFalse(sut.isPossibleAttackForPhone(phone, "field", true, "client"));
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

		assertThat(memoryAppender.countEventsForLogger(ValidationHelper.class)).isEqualTo(1);
		assertThat(memoryAppender.contains("Die übergebenen Daten dürfen nicht leer sein", Level.WARN)).isTrue();

		verifyNoMoreInteractions(alerts);
	}

	@ParameterizedTest
	@ValueSource(strings = { "$Test", "ﬁTest", "[", "¢", "¶", "", "æ", "Æ", "œ", "Œ", "@", "•Test", "°Test", "<SCRIPT",
			"SELECT iuaeuia uiae FROM", "INSERT    INTO" })
	void checkPossibleAttackTrue(String input) {

		assertTrue(sut.isPossibleAttackForRequiredValue(input, "Field1", false, "client"));

		assertThat(memoryAppender.countEventsForLogger(ValidationHelper.class)).isEqualTo(1);
		assertThat(memoryAppender.contains("Die übergebenen Eingabedaten sind nicht erlaubt", Level.WARN)).isTrue();
		assertThat(memoryAppender.contains("Field1", Level.WARN)).isTrue();
		assertThat(memoryAppender.contains(input, Level.WARN)).isTrue();

		verify(alerts).createAlertMessage(any(), contains(input));

		assertTrue(sut.isPossibleAttack(input, "Field2", false, "client"));

		assertThat(memoryAppender.countEventsForLogger(ValidationHelper.class)).isEqualTo(2);
		assertThat(memoryAppender.contains("Die übergebenen Eingabedaten sind nicht erlaubt", Level.WARN)).isTrue();
		assertThat(memoryAppender.contains("Field2", Level.WARN)).isTrue();
		assertThat(memoryAppender.contains(input, Level.WARN)).isTrue();

		verify(alerts, times(2)).createAlertMessage(any(), contains(input));
		verifyNoMoreInteractions(alerts);
	}

	@ParameterizedTest
	@ValueSource(strings = { "Test", "Haus", "iae dntrn dn" })
	void checkPossibleAttackFalse(String input) {

		assertFalse(sut.isPossibleAttackForRequiredValue(input, "Field", false, "client"));
		assertFalse(sut.isPossibleAttack(input, "Field", false, "client"));

		verifyNoInteractions(alerts);
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "  " })
	void checkPossibleAttackFalseForNull(String input) {

		assertFalse(sut.isPossibleAttack(input, "Field", false, "client"));

		verifyNoInteractions(alerts);
	}

	@ParameterizedTest
	@ValueSource(strings = { "$Testwert", "ﬁTestwert", "[Testwert", "¢Testwert", "¶Testwert", "Testwert", "æTestwert",
			"ÆTestwert", "œTestwert", "ŒTestwert", "@Testwert", "•Testwert", "°Testwert", "<SCRIPTTestwert",
			"SELECT iuaeuia uiae FROMTestwert", "INSERT    INTOTestwert" })
	void checkObfuscate(String input) {

		assertTrue(sut.isPossibleAttack(input, "Field", true, "client"));

		assertThat(memoryAppender.countEventsForLogger(ValidationHelper.class)).isEqualTo(1);
		assertThat(memoryAppender.contains("Die übergebenen Eingabedaten sind nicht erlaubt", Level.WARN)).isTrue();
		assertThat(memoryAppender.contains("Field", Level.WARN)).isTrue();
		assertThat(memoryAppender.contains("Tes*****", Level.WARN)).isTrue();

		verify(alerts).createAlertMessage(any(), contains("Tes*****"));
		verifyNoMoreInteractions(alerts);
	}
}
