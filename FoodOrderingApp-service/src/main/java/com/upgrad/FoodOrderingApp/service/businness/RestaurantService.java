package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
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
}
