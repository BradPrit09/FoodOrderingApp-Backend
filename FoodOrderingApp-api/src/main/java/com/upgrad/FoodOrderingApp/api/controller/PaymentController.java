package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.PaymentListResponse;
import com.upgrad.FoodOrderingApp.api.model.PaymentResponse;
import com.upgrad.FoodOrderingApp.api.model.RestaurantListResponse;
import com.upgrad.FoodOrderingApp.service.businness.PaymentService;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @RequestMapping(method = RequestMethod.GET, path = "/payment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PaymentListResponse> getAllPayments() {
        List<PaymentEntity> paymentList = paymentService.getAllPayments();
        List<PaymentResponse> paymentResponseList = convertentityToResponseObject(paymentList);
        PaymentListResponse listResponse = new PaymentListResponse().paymentMethods(paymentResponseList);
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }

    private List<PaymentResponse> convertentityToResponseObject(List<PaymentEntity> paymentList) {
        List<PaymentResponse> responseList = new ArrayList<>();
        for (PaymentEntity entity : paymentList) {
            PaymentResponse responseObject = new PaymentResponse();
            responseObject.setId(UUID.fromString(entity.getUuid()));
            responseObject.setPaymentName(entity.getPaymentName());
            responseList.add(responseObject);
        }
        return responseList;
    }
}
