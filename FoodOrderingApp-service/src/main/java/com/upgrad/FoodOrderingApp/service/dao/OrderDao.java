package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.Coupon;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.Orders;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;


    public Coupon getCouponDetailsByName(String couponName) {
        Query query = entityManager.createQuery("from Coupon as c where lower(c.couponName) like lower(concat('%',:couponName,'%'))");
        query.setParameter("couponName", couponName);
        Object singleResult = query.getSingleResult();
        Coupon coupon = new Coupon();
        if (singleResult instanceof Coupon) {
            coupon = (Coupon) singleResult;
        }
        return coupon;
    }

    public List<Orders> getOrdersForCustomer(CustomerEntity customer) {
        Query query = entityManager.createQuery("from Orders as o where o.customer.uuid=:uuid");
        query.setParameter("uuid", customer.getUuid());
        List resultList = query.getResultList();
        List<Orders> ordersList = new ArrayList<>();
        for (Object ent : resultList) {
            ordersList.add((Orders) ent);
        }
        return ordersList;
    }

    public Orders saveOrder(Orders savedOrder) {
        entityManager.persist(savedOrder);
        return savedOrder;
    }

    public Coupon getCouponByUUID(UUID couponId) {
        try {
            return entityManager
                    .createNamedQuery("getCouponByuuid", Coupon.class)
                    .setParameter("uuid", couponId.toString()).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
