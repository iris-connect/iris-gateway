package iris.location_service.utils;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

@Slf4j
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

	public static boolean isPossibleAttackForRequiredValue(String input, String message) {
		if (input == null || input.length() <= 0) {
			log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + message);
			return true;
		}

		return isPossibleAttack(input, message);
	}

	public static boolean isPossibleAttack(String input, String message) {

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

		if (input == null) {
			return false;
		}

		String inputUpper = input.toUpperCase();
		if (inputUpper.contains("<SCRIPT")
			|| inputUpper.contains("SELECT") && inputUpper.contains("FROM")
			|| StringUtils.startsWithAny(input, forbiddenSymbolsArray)) {
			log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + message);
			return true;
		}

		return false;
	}

}
