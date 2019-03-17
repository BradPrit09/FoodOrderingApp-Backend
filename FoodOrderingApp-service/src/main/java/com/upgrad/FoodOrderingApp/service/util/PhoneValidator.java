package com.upgrad.FoodOrderingApp.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to validate valid phone number
 */
public class PhoneValidator {

    private static final String PHONE_PATTERN = "\\d{10}";
    private Pattern pattern;
    private Matcher matcher;

    public PhoneValidator() {
        pattern = Pattern.compile(PHONE_PATTERN);
    }

    /**
     * method used to validate the phoneNumber
     *
     * @param phoneNumber string be validated
     * @return true if valid pincode else false.
     */
    public boolean validate(final String phoneNumber) {
        matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


//    public static void main(String[] args) {
//        String sPhoneNumber = "6028889999";
//        //String sPhoneNumber = "605-88899991";
//        //String sPhoneNumber = "605-888999A";
//
//        Pattern pattern = Pattern.compile("\\d{10}");
//        Matcher matcher = pattern.matcher(sPhoneNumber);
//
//        if (matcher.matches()) {
//            System.out.println("Phone Number Valid");
//        } else {
//            System.out.println("Phone Number must be in the form XXXXXXXXXX");
//        }
//    }
}
