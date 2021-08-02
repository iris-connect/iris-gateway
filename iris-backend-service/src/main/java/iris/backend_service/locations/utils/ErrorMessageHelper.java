package iris.backend_service.locations.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessageHelper {

	public static final String INVALID_INPUT_EXCEPTION_MESSAGE = "Die übergebenen Eingabedaten sind nicht erlaubt";
	public static final String INVALID_INPUT_STRING = "INVALID";
	public static final String MISSING_REQUIRED_INPUT_MESSAGE = "Die übergebenen Daten dürfen nicht leer sein";
}
