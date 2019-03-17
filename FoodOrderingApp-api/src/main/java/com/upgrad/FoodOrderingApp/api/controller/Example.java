package com.upgrad.FoodOrderingApp.api.controller;

import java.util.Base64;

public class Example {

    public static void main(String[] args) {
        String authorization = "Basic manoj joshi";
        byte[] decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
        System.out.println( decode);
    }
}
