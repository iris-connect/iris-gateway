package iris.location_service.utils;

import java.util.Arrays;
import java.util.stream.Stream;

public class ValidationHelper {

	public static final String regexEmail = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w-_]+\\.)+[\\w]+[\\w]$";
	public static final String regexPhone =
		"^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$|^((((\\()?0\\d{3,4}(\\))?)|(\\+49 (\\()?\\d{4}(\\))?))([/ -])(\\d{6}(-\\d{2})?))$"
			+ "|^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

	public static boolean isValidAndNotNullEmail(String email) {
		if (email == null)
			return false;
		return email.matches(regexEmail);
	}

	public static boolean isValidAndNotNullPhoneNumber(String phoneNumber) {
		if (phoneNumber == null)
			return false;
		return phoneNumber.matches(regexPhone);
	}

	public static boolean isNotShowingSignsForAttacks(String input) {
		if (input == null)
			return false;

		if (input.length() <= 0)
			return false;

		if (input.contains("<script"))
			return false;

		if (input.contains("SELECT") && input.contains("FROM"))
			return false;

		String[] forbiddenSymbolsArray = {
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
		Stream<String> forbiddenSymbolsStream = Arrays.stream(forbiddenSymbolsArray);
		int forbiddenSymbolCounter = (int) forbiddenSymbolsStream.filter(symbol -> input.startsWith(symbol) == true).count();

		if (forbiddenSymbolCounter > 0)
			return false;

		return true;
	}

}
