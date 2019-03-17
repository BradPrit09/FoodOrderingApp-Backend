package com.upgrad.FoodOrderingApp.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to validate valid phone number
 */
public class PinCodeValidator {
    private static final String PHONE_PATTERN = "\\d{6}";
    private Pattern pattern;
    private Matcher matcher;

    public PinCodeValidator() {
        pattern = Pattern.compile(PHONE_PATTERN);
    }

    /**
     * method used to validate the pincode
     *
     * @param pinCode string be validated
     * @return true if valid pincode else false.
     */
    public boolean validate(final String pinCode) {
        matcher = pattern.matcher(pinCode);
        return matcher.matches();
    }

//    public static void main(String[] args) {
//        PinCodeValidator validator = new PinCodeValidator();
//        boolean validate = validator.validate("400016");
//        System.out.println(validate);
//    }
}
