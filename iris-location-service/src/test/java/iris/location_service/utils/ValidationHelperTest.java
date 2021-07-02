package iris.location_service.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidationHelperTest {

    @ParameterizedTest
    @ValueSource(strings = {"a@b.de", "rick4711@yahoo.com", "r@sub.main.org", "r@foo-bar.org"})
    public void checkValidEmailAddresses(String email) {
        assertTrue(ValidationHelper.isValidAndNotNullEmail(email));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"","a@b", "a@b.de.", "rick4711yahoo.com", "@online.org"})
    public void checkInvalidEmailAddresses(String email) {
        assertFalse(ValidationHelper.isValidAndNotNullEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0123456789", "+4912345678", "+3412345678", "0172 111567"}) // other variants: "0172/111567", "0222 6578-22"
    public void checkValidPhoneNumbers(String phone) {
        assertTrue(ValidationHelper.isValidAndNotNullPhoneNumber(phone));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"","0123A56789", "01234%678", "0815 47+11", "0123456*89", "0", "12345678"})
    public void checkInvalidPhoneNumbers(String phone) {
        assertFalse(ValidationHelper.isValidAndNotNullEmail(phone));
    }

}
