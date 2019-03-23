package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Restaurant DAO class for accessing restaurant related functionality
 */
@Repository
public class RestaurantDao {

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Method used for acessing all details of restaurant
     *
     * @param restaurantList
     */
    public List<RestaurantEntity> getAllRestaurantDetails(List<RestaurantEntity> restaurantList) {
        Query query = entityManager.createQuery("from RestaurantEntity");
        List resultList = query.getResultList();
        List<RestaurantEntity> newList = new ArrayList<>();
        for (Object rtObj : resultList) {
            newList.add((RestaurantEntity) rtObj);
        }
        return newList;
    }
}
