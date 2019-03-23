package com.upgrad.FoodOrderingApp.api.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Example {

    public static void main(String[] args) {
        List<String> authorization = new ArrayList<>();
        authorization.add("1");
        authorization.add("2");
        authorization.add("3");
        authorization.add("4");
        authorization.add("5");
//        byte[] decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
        System.out.println(authorization);
    }
}
