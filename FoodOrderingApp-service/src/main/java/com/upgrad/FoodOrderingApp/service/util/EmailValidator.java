package com.upgrad.FoodOrderingApp.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common utility class to validate email id.
 */
public class EmailValidator {


    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Validate emailid with regular expression
     *
     * @param emailid emailid for validation
     * @return true valid emailid, false invalid emailid
     */
    public boolean validate(final String emailid) {

        matcher = pattern.matcher(emailid);
        return matcher.matches();

    }

    /**
     * Simple test to confirm it is working properly
     *
     * @param args
     */
    /*public static void main(String[] args) {
        String emailid = "111manojo1212@gmail.net";
        EmailValidator validator = new EmailValidator();
        boolean validate = validator.validate(emailid);
        System.out.println(validate);
    }*/
}
