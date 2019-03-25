package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.hibernate.criterion.CriteriaQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    public List<RestaurantEntity> getRestaurantsByName(String restaurantName) {
        Query query = entityManager.createQuery("from RestaurantEntity as r where lower(r.restaurantName) like lower(concat('%',:restaurantname,'%'))");
        query.setParameter("restaurantname", restaurantName);
        List resultList = query.getResultList();
        List<RestaurantEntity> newList = new ArrayList<>();
        for (Object rtObj : resultList) {
            newList.add((RestaurantEntity) rtObj);
        }
        return newList;
    }

    public RestaurantEntity getRestaurantByUUID(String restaurantId) {
        try {
            return entityManager.createNamedQuery("getRestaurantByUUID", RestaurantEntity.class)
                    .setParameter("uuid", restaurantId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public RestaurantEntity updateRestaurantDetails(RestaurantEntity restaurantByUUID) {
        entityManager.persist(restaurantByUUID);
        return restaurantByUUID;
    }
}
