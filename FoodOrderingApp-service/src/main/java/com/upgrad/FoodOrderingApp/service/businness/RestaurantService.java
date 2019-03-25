package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    RestaurantDao restaurantDao;


    @Transactional(propagation = Propagation.REQUIRED)
    public List<RestaurantEntity> getAllRestaurantDetails() {
        List<RestaurantEntity> restaurantList = new ArrayList<>();
        restaurantList = restaurantDao.getAllRestaurantDetails(restaurantList);
        return restaurantList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<RestaurantEntity> getRestaurantsByName(String restaurantName) {

        return restaurantDao.getRestaurantsByName(restaurantName);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity getRestaurantByRestaurantId(String restaurantId) throws RestaurantNotFoundException {
        RestaurantEntity restaurantEntity = restaurantDao.getRestaurantByUUID(restaurantId);
        if (restaurantEntity == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        return restaurantEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity updateRestaurantDetails(Double customerRating, String restaurantUUID) {
        RestaurantEntity restaurantByUUID = restaurantDao.getRestaurantByUUID(restaurantUUID);
        Double originalRating = restaurantByUUID.getCustomerRating();
        Integer originalCustomersRated = restaurantByUUID.getNumberOfCustomersRated();
        Double total = originalCustomersRated * originalRating;
        restaurantByUUID.setCustomerRating(customerRating);
        restaurantByUUID.setNumberOfCustomersRated((int) (total / customerRating));
        RestaurantEntity updatedRestaurant = restaurantDao.updateRestaurantDetails(restaurantByUUID);
        return updatedRestaurant;
    }
}
