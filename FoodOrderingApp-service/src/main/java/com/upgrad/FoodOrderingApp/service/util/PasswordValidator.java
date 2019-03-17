package com.upgrad.FoodOrderingApp.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to validate password of customer
 */
public class PasswordValidator {
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[A-Z])(?=.*[#@$%&*!^]).{8,255})";
    Pattern pattern;
    Matcher matcher;

    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    /**
     * method to validate the password
     *
     * @param password password string
     * @return true is valid password else false
     */
    public boolean validate(String password) {
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

//    public static void main(String[] args) {
//        PasswordValidator validator = new PasswordValidator();
//        System.out.println(validator.validate("mJosh^1982"));
//    }

}
