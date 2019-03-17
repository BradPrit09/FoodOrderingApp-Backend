package com.upgrad.FoodOrderingApp.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrad.FoodOrderingApp.api.model.PaymentListResponse;
import com.upgrad.FoodOrderingApp.service.businness.PaymentService;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// This class contains all the test cases regarding the payment controller
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService mockPaymentService;

    //This test case passes when you are able to retrieve all payment methods that exist in the database.
    @Test
    public void shouldGetAllPaymentMethods() throws Exception {
        final PaymentEntity paymentEntity = new PaymentEntity();
        final String paymentId = UUID.randomUUID().toString();
        paymentEntity.setUuid(paymentId);
        paymentEntity.setPaymentName("samplePaymentName");

        when(mockPaymentService.getAllPaymentMethods())
                .thenReturn(Collections.singletonList(paymentEntity));

        final String response = mockMvc
                .perform(get("/payment").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final PaymentListResponse paymentResponses = new ObjectMapper().readValue(response, PaymentListResponse.class);
        assertEquals(paymentResponses.getPaymentMethods().size(), 1);
        assertEquals(paymentResponses.getPaymentMethods().get(0).getId().toString(), paymentId);
        assertEquals(paymentResponses.getPaymentMethods().get(0).getPaymentName(), "samplePaymentName");
        verify(mockPaymentService, times(1)).getAllPaymentMethods();
    }

}