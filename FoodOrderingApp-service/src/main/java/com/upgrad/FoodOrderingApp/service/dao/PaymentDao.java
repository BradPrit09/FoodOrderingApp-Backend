package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public PaymentEntity checkPayment(UUID paymentId) {
        try {
            return entityManager.createNamedQuery("getPaymentByUUid", PaymentEntity.class)
                    .setParameter("uuid", paymentId.toString())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
