package com.upgrad.FoodOrderingApp.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrad.FoodOrderingApp.api.model.ItemListResponse;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
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

import static com.upgrad.FoodOrderingApp.service.common.ItemType.NON_VEG;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// This class contains all the test cases regarding the item controller
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService mockItemService;

    @MockBean
    private RestaurantService mockRestaurantService;

    //This test case passes when you are able to fetch top 5 items based on the number of times they were ordered.
    @Test
    public void shouldGetItemsByPopularity() throws Exception {
        final RestaurantEntity restaurantEntity = new RestaurantEntity();
        when(mockRestaurantService.restaurantByUUID("some_restaurant_id"))
                .thenReturn(restaurantEntity);

        final ItemEntity itemEntity = new ItemEntity();
        final String itemId = UUID.randomUUID().toString();
        itemEntity.setUuid(itemId);
        itemEntity.setType(NON_VEG);
        when(mockItemService.getItemsByPopularity(restaurantEntity))
                .thenReturn(Collections.singletonList(itemEntity));

        final String responseString = mockMvc
                .perform(get("/item/restaurant/some_restaurant_id")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final ItemListResponse itemListResponse = new ObjectMapper().readValue(responseString, ItemListResponse.class);
        assertEquals(itemListResponse.size(), 1);
        assertEquals(itemListResponse.get(0).getId().toString(), itemId);

    }

    //This test case passes when you have handled the exception of trying to fetch most popular items of a restaurant,
    // but the restaurant id you gave does not exist.
    @Test
    public void shouldNotGetItemsByPopularityIfRestaurantDoesNOtExistForGivenId() throws Exception {
        when(mockRestaurantService.restaurantByUUID("some_restaurant_id"))
                .thenThrow(new RestaurantNotFoundException("RNF-001", "No restaurant by this id"));

        mockMvc
                .perform(get("/item/restaurant/some_restaurant_id").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value("RNF-001"));
    }

}