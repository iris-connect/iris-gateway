package iris.backend_service.locations.utils;

import static org.apache.commons.lang3.StringUtils.*;

import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.messages.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor()
public class ValidationHelper {

	public static final Pattern REGEX_EMAIL = Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w-_]+\\.)+[\\w]+[\\w]$");
	public static final Pattern REGEX_PHONE = Pattern.compile("^\\+?[0-9][\\/\\.\\(\\)\\s\\-0-9]{6,}?[0-9]$");

	private static final String[] FORBIDDEN_SYMBOLS = {
			"=",
			"<",
			">",
			"!",
			"\"",
			"§",
			"$",
			"%",
			"&",
			"/",
			"(",
			")",
			"?",
			"´",
			"`",
			"¿",
			"≠",
			"¯",
			"}",
			"·",
			"{",
			"˜",
			"\\",
			"]",
			"^",
			"ﬁ",
			"[",
			"¢",
			"¶",
			"“",
			"¡",
			"¬",
			"”",
			"#",
			"£",
			"+",
			"*",
			"±",
			"",
			"‘",
			"’",
			"'",
			"-",
			"_",
			".",
			":",
			"…",
			"÷",
			"∞",
			";",
			"˛",
			"æ",
			"Æ",
			"œ",
			"Œ",
			"@",
			"•",
			"°",
			"„" };

	private static final String[][] FORBIDDEN_KEYWORDS = {
			{ "<SCRIPT" }, { "JAVASCRIPT:" }, { "SELECT", "FROM" }, { "INSERT", "INTO" }, { "UPDATE", "SET" },
			{ "DELETE", "FROM" }, { "CREATE", "TABLE" }, { "DROP", "TABLE" }, { "ALTER", "TABLE" }, { "CREATE", "INDEX" },
			{ "DROP", "INDEX" }, { "CREATE", "VIEW" }, { "DROP", "VIEW" }
	};

	@Value("${iris.locations.post-limit:5000}")
	private int postLimit;

	public static boolean isValidAndNotNullEmail(String email) {
		if (email == null)
			return false;
		return REGEX_EMAIL.matcher(email).matches();
	}

	public static boolean isValidAndNotNullPhoneNumber(String phoneNumber) {
		if (phoneNumber == null)
			return false;
		return REGEX_PHONE.matcher(phoneNumber).matches();
	}

	private final AlertService alerts;

	public boolean isPostOutOfLimit(List<LocationInformation> locationList, String client) {

		if (locationList.size() > postLimit) {

			var msg = String.format(
					"Input from client `%s` contains %d locations at once. We prevent this as a possible attack! {threshold = %d}",
					client,
					locationList.size(), postLimit);
			log.warn(msg);
			alerts.createAlertMessage("Input validation - to many location posted", msg);

			return true;
		}

		return false;
	}

	public boolean isPossibleAttackForRequiredValue(String input, String field, boolean obfuscateLogging, String client) {
		if (isBlank(input)) {
			log.warn(ErrorMessageHelper.MISSING_REQUIRED_INPUT_MESSAGE + " - {}", field);
			return true;
		}

		return isPossibleAttack(input, field, obfuscateLogging, client);
	}

	public boolean isPossibleAttack(String input, String field, boolean obfuscateLogging, String client) {
		return isPossibleAttack(input, field, obfuscateLogging, client, FORBIDDEN_SYMBOLS);
	}

	public boolean isPossibleAttackForPhone(String input, String field, boolean obfuscateLogging, String client) {
		return isPossibleAttack(input, field, obfuscateLogging, client, ArrayUtils.removeElement(FORBIDDEN_SYMBOLS, "+"));
	}

	public boolean isPossibleAttack(String input, String field, boolean obfuscateLogging, String client,
			String[] forbidenSymbols) {

		if (input == null) {
			return false;
		}

		String inputUpper = input.toUpperCase();
		Optional<Range<Integer>> range;
		if ((range = testKeywords(inputUpper, FORBIDDEN_KEYWORDS)).isPresent()
				|| (range = testSymbols(input, forbidenSymbols)).isPresent()) {

			var logableValue = calculateLogableValue(input, obfuscateLogging, range.get());

			log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - {}: {}", field,
					logableValue);

			alerts.createAlertMessage("Input validation - possible attack",
					String.format("Input `%s` from client `%s` contain the character or keyword `%s` that is a potential attack!",
							field, client, logableValue));

			return true;
		}

		return false;
	}

	private static Optional<Range<Integer>> testKeywords(String str, String[]... keywords) {

		return Arrays.stream(keywords)
				.map(it -> testKeywordsAndLinked(str, it))
				.flatMap(Optional<Range<Integer>>::stream)
				.findFirst();
	}

	private static Optional<Range<Integer>> testKeywordsAndLinked(String str, String... keywords) {

		for (var keyword : keywords) {
			if (indexOf(str, keyword) < 0) {
				return Optional.empty();
			}
		}

		var lastKeyword = keywords[keywords.length - 1];

		return Optional.of(Range.between(indexOf(str, keywords[0]), indexOf(str, lastKeyword) + lastKeyword.length() - 1));
	}

	private static Optional<Range<Integer>> testSymbols(String input, String[] forbiddenSymbolsArray) {

		if (StringUtils.startsWithAny(input, forbiddenSymbolsArray)) {
			return Optional.of(Range.is(0));
		}

		return Optional.empty();
	}

	private static String calculateLogableValue(String input, boolean obfuscateLogging, Range<Integer> range) {

		return obfuscateLogging
				? LoggingHelper.obfuscateOutsiteExtRange(input, range)
				: input;
	}
}
