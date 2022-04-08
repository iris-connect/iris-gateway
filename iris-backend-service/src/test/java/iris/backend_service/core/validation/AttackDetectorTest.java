package iris.backend_service.core.validation;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import iris.backend_service.MemoryAppender;
import iris.backend_service.messages.AlertService;

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
class AttackDetectorTest {

	@Mock
	AlertService alerts;

	@InjectMocks
	AttackDetector sut;

	private MemoryAppender memoryAppender;

	@BeforeEach
	public void setup() {
		Logger logger = (Logger) LoggerFactory.getLogger(AttackDetector.class);
		memoryAppender = new MemoryAppender();
		memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
		logger.setLevel(Level.DEBUG);
		logger.addAppender(memoryAppender);
		memoryAppender.start();
	}

	@ParameterizedTest
	@ValueSource(strings = { "$Test", "ﬁTest", "[", "¢", "¶", "", "æ", "Æ", "œ", "Œ", "@", "•Test", "°Test", "<SCRIPT",
			"SELECT iuaeuia uiae FROM", "INSERT    INTO" })
	void checkPossibleAttackTrue(String input) {

		assertTrue(sut.isPossibleAttack(input, "Field", false, "client"));

		assertThat(memoryAppender.countEventsForLogger(AttackDetector.class)).isEqualTo(1);
		assertThat(memoryAppender.contains("Die übergebenen Eingabedaten sind nicht erlaubt", Level.WARN)).isTrue();
		assertThat(memoryAppender.contains("Field", Level.WARN)).isTrue();
		assertThat(memoryAppender.contains(input, Level.WARN)).isTrue();

		verify(alerts, only()).createAlertMessage(any(), contains(input));
	}

	@ParameterizedTest
	@ValueSource(strings = { "Test", "Haus", "iae dntrn dn" })
	void checkPossibleAttackFalse(String input) {

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

		assertThat(memoryAppender.countEventsForLogger(AttackDetector.class)).isEqualTo(1);
		assertThat(memoryAppender.contains("Die übergebenen Eingabedaten sind nicht erlaubt", Level.WARN)).isTrue();
		assertThat(memoryAppender.contains("Field", Level.WARN)).isTrue();
		assertThat(memoryAppender.contains("Tes*****", Level.WARN)).isTrue();

		verify(alerts).createAlertMessage(any(), contains("Tes*****"));
		verifyNoMoreInteractions(alerts);
	}
}
