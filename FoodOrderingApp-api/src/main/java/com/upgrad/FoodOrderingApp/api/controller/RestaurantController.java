package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddress;
import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddressState;
import com.upgrad.FoodOrderingApp.api.model.RestaurantList;
import com.upgrad.FoodOrderingApp.api.model.RestaurantListResponse;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * This class exposes rest apis for restaurant related operations.
 */
@CrossOrigin
@RestController
@RequestMapping("/")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getAllRestaurantDetails() {

        List<RestaurantEntity> allRestaurantDetails = restaurantService.getAllRestaurantDetails();
        List<RestaurantList> responseList = convertRestaurantEntityListToRestaurantListResponse(allRestaurantDetails);
        RestaurantListResponse finalResponse = new RestaurantListResponse();
        finalResponse.setRestaurants(responseList);
        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }

    /**
     * Helper method to convert restaurant entity to restaurantlistresponse
     *
     * @param allRestaurantDetails
     * @return List of RestaurantList objects
     */
    private List<RestaurantList> convertRestaurantEntityListToRestaurantListResponse(List<RestaurantEntity> allRestaurantDetails) {
        List<RestaurantList> responseList = new ArrayList<>();
        for (RestaurantEntity entityObj : allRestaurantDetails) {
            RestaurantList restaurantListObj = new RestaurantList();
            RestaurantDetailsResponseAddress responseAddress = new RestaurantDetailsResponseAddress();
            //Enter address details
            enterAddressDetails(entityObj, responseAddress);
            //Enter state details
            enterStateDetails(entityObj, restaurantListObj, responseAddress);
            //Enter restaurant details
            enterRestaurantDetails(entityObj, restaurantListObj);
            //get the category and set it in the right object
            enterCategoryDetails(entityObj, restaurantListObj);
            responseList.add(restaurantListObj);
        }
        return responseList;
    }


    /**
     * Helper method to fill up category details in restaurant response object
     *
     * @param entityObj         restaurant entity object
     * @param restaurantListObj restaurantList object
     */
    private void enterCategoryDetails(RestaurantEntity entityObj, RestaurantList restaurantListObj) {
        String categoryListString = "";
        for (CategoryEntity entity : entityObj.getCategories()) {
            categoryListString += entity.getCategoryName() + ",";
        }
        restaurantListObj.setCategories(categoryListString.substring(0, categoryListString.length() - 1));
    }

    /**
     * helper method to fill up remaining details of restaurant in restaurant response object.
     *
     * @param entityObj         restaurant entity object
     * @param restaurantListObj restaurantList object
     */
    private void enterRestaurantDetails(RestaurantEntity entityObj, RestaurantList restaurantListObj) {
        restaurantListObj.setRestaurantName(entityObj.getRestaurantName());
        restaurantListObj.setAveragePrice(entityObj.getAveragePriceForTwo());
        restaurantListObj.setPhotoURL(entityObj.getPhoto_url());
        restaurantListObj.setCustomerRating(new BigDecimal(entityObj.getCustomerRating()));
        restaurantListObj.setNumberCustomersRated(entityObj.getNumberOfCustomersRated());
        restaurantListObj.setAveragePrice(entityObj.getAveragePriceForTwo());
    }

    /**
     * Helper method to enter State details in restaurant response object.
     *
     * @param entityObj         restaurant entity object
     * @param restaurantListObj restaurantList object
     * @param responseAddress   responseAddress object
     */
    private void enterStateDetails(RestaurantEntity entityObj, RestaurantList restaurantListObj, RestaurantDetailsResponseAddress responseAddress) {
        RestaurantDetailsResponseAddressState stateObj = new RestaurantDetailsResponseAddressState();
        stateObj.setId(UUID.fromString(entityObj.getUuid()));
        stateObj.setStateName(entityObj.getAddressId().getStateEntity().getStateName());
        responseAddress.setState(stateObj);
        restaurantListObj.setAddress(responseAddress);
    }

    /**
     * Helper method to enter address details in restaurant response object.
     *
     * @param entityObj       restaurant entity object
     * @param responseAddress responseAddress object
     */
    private void enterAddressDetails(RestaurantEntity entityObj, RestaurantDetailsResponseAddress responseAddress) {
        responseAddress.setCity(entityObj.getAddressId().getCity());
        responseAddress.setFlatBuildingName(entityObj.getAddressId().getFlatBillNumber());
        responseAddress.setLocality(entityObj.getAddressId().getLocality());
        responseAddress.setId(UUID.fromString(entityObj.getAddressId().getUuid()));
    }
}
