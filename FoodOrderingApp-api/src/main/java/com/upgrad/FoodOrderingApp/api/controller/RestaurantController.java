package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;


/**
 * This class exposes rest apis for restaurant related operations.
 */
@CrossOrigin
@RestController
@RequestMapping("/")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CustomerService customerService;

    /**
     * Method used for getting all restaurants
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getAllRestaurantDetails() {
        List<RestaurantEntity> allRestaurantDetails = restaurantService.getAllRestaurantDetails();
        List<RestaurantList> responseList = convertRestaurantEntityListToRestaurantListResponse(allRestaurantDetails);
        RestaurantListResponse finalResponse = new RestaurantListResponse();
        finalResponse.setRestaurants(responseList);
        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }

    /**
     * Method used for get restaunrant by Name
     *
     * @param restaurantName
     * @return
     * @throws RestaurantNotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/name/{restaurant_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getRestaurantsByName(@PathVariable("restaurant_name") final String restaurantName) {
        try {
            //Check if restaurant field is empty
            if (restaurantName.trim().isEmpty()) {
                throw new RestaurantNotFoundException("RNF-003", "Restaurant name field should not be empty");
            }
        } catch (RestaurantNotFoundException e) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage(e.getErrorMessage());
            response.setCode(e.getCode());
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
        List<RestaurantEntity> allRestaurantDetails = restaurantService.getRestaurantsByName(restaurantName);
        List<RestaurantList> responseList = convertRestaurantEntityListToRestaurantListResponse(allRestaurantDetails);
        RestaurantListResponse finalResponse = new RestaurantListResponse();
        finalResponse.setRestaurants(responseList);
        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }


    /**
     * Method used for getting restaurant by Category uuid
     *
     * @param categoryId
     * @return
     * @throws CategoryNotFoundException
     */
    ///restaurant/category/{category_id}
    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantsByCategory(@PathVariable("category_id") final String categoryId) throws CategoryNotFoundException {

        CategoryEntity categoryExists = null;
        if (categoryId.trim().isEmpty()) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }
        categoryExists = categoryService.checkCategoryExists(categoryId);

        List<RestaurantEntity> restaurantList = categoryExists.getRestaurantList();
        List<RestaurantList> responseList = convertRestaurantEntityListToRestaurantListResponse(restaurantList);
        RestaurantListResponse finalResponse = new RestaurantListResponse();
        finalResponse.setRestaurants(responseList);
        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }


    /**
     * Method used for updating restaurant details.
     *
     * @param customerRating
     * @param restaurantUUID
     * @param accessToken
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/api/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantUpdatedResponse> updateRestaurantDetails(@RequestParam(name = "customerrating") Double customerRating, @PathVariable("restaurant_id") final String restaurantUUID, @RequestHeader("authorization") final String accessToken) {
        RestaurantUpdatedResponse restaurantUpdatedResponse = null;

        try {
            CustomerAuthEntity customerAuthEntity = customerService.getCustomerAuthEntity(accessToken);
        } catch (AuthorizationFailedException e) {
            restaurantUpdatedResponse = new RestaurantUpdatedResponse().status(e.getCode() + " " + e.getErrorMessage());
            return new ResponseEntity<>(restaurantUpdatedResponse, HttpStatus.FORBIDDEN);
        }

        try {
            if (restaurantUUID.trim().isEmpty()) {
                throw new RestaurantNotFoundException("RNF-002", "Restaurant id field should not be empty");
            }

            if (customerRating < 1 || customerRating > 5) {
                throw new InvalidRatingException("IRE-001", "Restaurant should be in the range of 1 to 5");
            }
        } catch (RestaurantNotFoundException e) {
            restaurantUpdatedResponse = new RestaurantUpdatedResponse().status(e.getCode() + " " + e.getErrorMessage());
            return new ResponseEntity<>(restaurantUpdatedResponse, HttpStatus.FORBIDDEN);
        } catch (InvalidRatingException inve) {
            restaurantUpdatedResponse = new RestaurantUpdatedResponse().status(inve.getCode() + " " + inve.getErrorMessage());
            return new ResponseEntity<>(restaurantUpdatedResponse, HttpStatus.FORBIDDEN);
        }

        RestaurantEntity restaurantEntity = restaurantService.updateRestaurantDetails(customerRating, restaurantUUID);

        restaurantUpdatedResponse = new RestaurantUpdatedResponse()
                .id(UUID.fromString(restaurantEntity.getUuid()))
                .status("RESTAURANT RATING UPDATED SUCCESSFULLY");
        return new ResponseEntity<>(restaurantUpdatedResponse, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/api/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantDetailsResponse> getRestaurantsByRestaurantId(@PathVariable("restaurant_id") final String restaurantId) {

        RestaurantEntity restaurantEntity = null;
        try {
            if (restaurantId.trim().isEmpty()) {
                throw new RestaurantNotFoundException("RNF-002", "Restaurant id field should not be empty");
            }

            restaurantEntity = restaurantService.getRestaurantByRestaurantId(restaurantId);
        } catch (RestaurantNotFoundException e) {
            RestaurantDetailsResponse detailsResponse = new RestaurantDetailsResponse().restaurantName(e.getErrorMessage());
            return new ResponseEntity<>(detailsResponse, HttpStatus.FORBIDDEN);
        }
        RestaurantDetailsResponse detailsResponse = convertListToResponseObject(restaurantEntity);
        return new ResponseEntity<>(detailsResponse, HttpStatus.OK);
    }

    private RestaurantDetailsResponse convertListToResponseObject(RestaurantEntity restaurantEntity) {
        RestaurantDetailsResponse detailsResponse = new RestaurantDetailsResponse();
        detailsResponse.setId(UUID.fromString(restaurantEntity.getUuid()));
        detailsResponse.setRestaurantName(restaurantEntity.getRestaurantName());
        detailsResponse.setPhotoURL(restaurantEntity.getPhoto_url());
        detailsResponse.setCustomerRating(new BigDecimal(restaurantEntity.getCustomerRating()));
        detailsResponse.setAveragePrice(restaurantEntity.getAveragePriceForTwo());
        detailsResponse.setNumberCustomersRated(restaurantEntity.getNumberOfCustomersRated());
        //set address and state
        RestaurantDetailsResponseAddress address = new RestaurantDetailsResponseAddress();
        RestaurantDetailsResponseAddressState state = new RestaurantDetailsResponseAddressState();
        address.setPincode(restaurantEntity.getAddressId().getPinCode());
        address.setId(UUID.fromString(restaurantEntity.getAddressId().getUuid()));
        address.setLocality(restaurantEntity.getAddressId().getLocality());
        address.setFlatBuildingName(restaurantEntity.getAddressId().getFlatBillNumber());
        address.setCity(restaurantEntity.getAddressId().getCity());
        state.setId(UUID.fromString(restaurantEntity.getAddressId().getStateEntity().getUuid()));
        state.setStateName(restaurantEntity.getAddressId().getStateEntity().getStateName());
        address.setState(state);


        Set<CategoryEntity> categories = restaurantEntity.getCategories();
        List<CategoryList> categoryListList = new ArrayList<>();

        for (CategoryEntity entity : categories) {
            CategoryList categoryList = new CategoryList();
            categoryList.setId(UUID.fromString(entity.getUuid()));
            categoryList.setCategoryName(entity.getCategoryName());
            List<ItemEntity> itemList = entity.getItemList();
            List<ItemList> itemsList = new ArrayList<>();
            //add the item values in this for loop
            for (ItemEntity entity1 : itemList) {
                ItemList itemVal = new ItemList();
                itemVal.setId(UUID.fromString(entity1.getUuid()));
                itemVal.setItemName(entity1.getItemName());
                itemVal.setPrice(entity1.getPrice());
                if (entity1.getType().equalsIgnoreCase("0")) {
                    itemVal.setItemType(ItemList.ItemTypeEnum.VEG);
                } else {
                    itemVal.setItemType(ItemList.ItemTypeEnum.NON_VEG);
                }
                itemsList.add(itemVal);
            }
            //include items in the categorylist
            categoryList.setItemList(itemsList);
            //include the category value in the categorylist
            categoryListList.add(categoryList);
        }
        detailsResponse.setCategories(categoryListList);
        return detailsResponse;
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

        List<String> categoryStringList = new ArrayList<>();
        String categoryListString = "";
        for (CategoryEntity entity : entityObj.getCategories()) {
            //  categoryListString += entity.getCategoryName() + ",";
            categoryStringList.add(entity.getCategoryName());
        }

        Collections.sort(categoryStringList);
        categoryListString = categoryStringList.toString();
//        restaurantListObj.setCategories(categoryListString.substring(1, categoryListString.length() - 1));
        restaurantListObj.setCategories(categoryListString.substring(1, categoryListString.length() - 1));
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
