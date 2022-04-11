package iris.backend_service.core.validation;

import static org.apache.commons.lang3.StringUtils.*;

import iris.backend_service.core.logging.LoggingHelper;
import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.messages.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor()
public class AttackDetector {

	public static final String INVALID_INPUT_EXCEPTION_MESSAGE = "Die übergebenen Eingabedaten sind nicht erlaubt";

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
		if ((range = findAnyOfKeywordTuples(inputUpper, FORBIDDEN_KEYWORDS)).isPresent()
				|| (range = testSymbols(input, forbidenSymbols)).isPresent()) {

			var logableValue = calculateLogableValue(input, obfuscateLogging, range.get());

			log.warn(INVALID_INPUT_EXCEPTION_MESSAGE + " - {}: {}", field,
					logableValue);

			alerts.createAlertMessage("Input validation - possible attack",
					String.format("Input `%s` from client `%s` contain the character or keyword `%s` that is a potential attack!",
							field, client, logableValue));

			return true;
		}

		return false;
	}

	private static Optional<Range<Integer>> findAnyOfKeywordTuples(String str, String[][] keywords) {

		return Arrays.stream(keywords)
				.map(it -> findKeywordTuple(str, it))
				.flatMap(Optional<Range<Integer>>::stream)
				.findFirst();
	}

	/**
	 * Searches for the keyword tuple in the input string.
	 *
	 * @param input String to be tested
	 * @param keywordTuple tuple with keywords to find
	 * @return If found: range in tested string starting at beginning of first keyword, ending at end of last keyword.
	 */
	private static Optional<Range<Integer>> findKeywordTuple(String str, String[] keywordTuples) {

		for (var keyword : keywordTuples) {
			if (indexOf(str, keyword) < 0) {
				return Optional.empty();
			}
		}

		var lastKeyword = keywordTuples[keywordTuples.length - 1];

		return Optional
				.of(Range.between(indexOf(str, keywordTuples[0]), indexOf(str, lastKeyword) + lastKeyword.length() - 1));
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
