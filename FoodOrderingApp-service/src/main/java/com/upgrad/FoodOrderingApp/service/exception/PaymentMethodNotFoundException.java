package com.upgrad.FoodOrderingApp.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * PaymentMethodNotFoundException is thrown when the payment method asked for does not exist in the database.
 */
public class PaymentMethodNotFoundException extends Exception {
    private final String code;
    private final String errorMessage;

    public PaymentMethodNotFoundException(final String code, final String errorMessage) {
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



