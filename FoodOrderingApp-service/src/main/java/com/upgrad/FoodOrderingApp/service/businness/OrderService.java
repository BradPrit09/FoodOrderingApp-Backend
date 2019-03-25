package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.entity.Coupon;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.Orders;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public Coupon getCouponDetails(String couponName) throws CouponNotFoundException {

        if (couponName.trim().isEmpty()) {
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        }

        Coupon coupon = orderDao.getCouponDetailsByName(couponName);
        if (coupon == null) {
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        }
        return coupon;
    }

    public List<Orders> getOrdersForCustomer(CustomerEntity customer) {
        List<Orders> ordersList = orderDao.getOrdersForCustomer(customer);
        return ordersList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Orders saveOrder(Orders savedOrder) {
        return orderDao.saveOrder(savedOrder);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Coupon getCouponDetailsByID(UUID couponId) throws CouponNotFoundException {
        Coupon coupon = orderDao.getCouponByUUID(couponId);
        if (coupon == null) {
            throw new CouponNotFoundException("CPF-002", "No coupon by this id");
        }
        return coupon;
    }
}
