package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentDao {

    @PersistenceContext
    EntityManager entityManager;


    public List<PaymentEntity> getAllPayments() {
        Query query = entityManager.createQuery("from PaymentEntity");
        List resultList = query.getResultList();
        List<PaymentEntity> newList = new ArrayList<>();
        for (Object newObj : resultList) {
            newList.add((PaymentEntity) newObj);
        }
        return newList;
    }
}
