package iris.location_service.utils;

public class ValidationHelper {
    public static final String regexEmail = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    public static final String regexPhone =
            "^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$|^((((\\()?0\\d{4}(\\))?)|(\\+49 (\\()?\\d{4}(\\))?))([/ -])(\\d{6}(-\\d{2})?))$"
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
}
