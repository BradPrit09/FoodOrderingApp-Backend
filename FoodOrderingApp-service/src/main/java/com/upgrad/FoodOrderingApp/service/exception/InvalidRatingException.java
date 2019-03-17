package com.upgrad.FoodOrderingApp.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * InvalidRatingException is thrown when customer tries to give a rating below 1 or above 5.
 */
public class InvalidRatingException extends Exception {
    private final String code;
    private final String errorMessage;

    public InvalidRatingException(final String code, final String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

