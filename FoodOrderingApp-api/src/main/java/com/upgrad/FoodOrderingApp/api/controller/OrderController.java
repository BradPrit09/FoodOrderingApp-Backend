package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/")
public class OrderController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * Method used to get top orders for a restaurant
     *
     * @return
     * @throws RestaurantNotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{coupon_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getCouponByName(@PathVariable("coupon_name") final String couponName, @RequestHeader final String accessToken) {
        CustomerAuthEntity customerAuthEntity = null;
        try {
            customerAuthEntity = customerService.getCustomerAuthEntity(accessToken);
        } catch (AuthorizationFailedException e) {
            return getResponseEntity(e.getCode(), e.getErrorMessage());
        }
        Coupon couponDetails = null;
        try {
            couponDetails = orderService.getCouponDetails(couponName);
        } catch (CouponNotFoundException e) {
            return getResponseEntity(e.getCode(), e.getErrorMessage());
        }
        CouponDetailsResponse response = new CouponDetailsResponse()
                .couponName(couponDetails.getCouponName())
                .id(UUID.fromString(couponDetails.getUuid()))
                .percent(couponDetails.getPercent());
        return new ResponseEntity(response, HttpStatus.OK);
    }


    /**
     * Method used to get top orders for a restaurant
     *
     * @return
     * @throws RestaurantNotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, path = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getPastOrders(@RequestHeader final String accessToken) {
        CustomerAuthEntity customerAuthEntity = null;
        try {
            customerAuthEntity = customerService.getCustomerAuthEntity(accessToken);
        } catch (AuthorizationFailedException e) {
            return getResponseEntity(e.getCode(), e.getErrorMessage());
        }

        List<Orders> ordersList = orderService.getOrdersForCustomer(customerAuthEntity.getCustomer());
        CustomerEntity customerEntity = customerAuthEntity.getCustomer();
        List<OrderList> listOfOrderForResponse = new ArrayList<>();
        //CustomerOrderResponse
        populateOrderData(ordersList, listOfOrderForResponse);

        CustomerOrderResponse orderResponse = new CustomerOrderResponse();
        orderResponse.setOrders(listOfOrderForResponse);

        return new ResponseEntity(orderResponse, HttpStatus.OK);
    }


    /**
     * Rest Endpoint method implementation used for signing up customer with all details.
     *
     * @param saveOrderRequest request object containing order details.
     * @return ResponseEntity containing user response
     * @throws SignUpRestrictedException exception thrown in case username of email id are same.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/order", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity signUp(final SaveOrderRequest saveOrderRequest, @RequestHeader final String accessToken) {
        CustomerAuthEntity customerAuthEntity = null;
        try {
            customerAuthEntity = customerService.getCustomerAuthEntity(accessToken);
        } catch (AuthorizationFailedException e) {
            return getResponseEntity(e.getCode(), e.getErrorMessage());
        }


        try {
            Coupon coupon = orderService.getCouponDetailsByID(saveOrderRequest.getCouponId());
        } catch (CouponNotFoundException e) {
            return getResponseEntity(e.getCode(), e.getErrorMessage());
        }

        try {
            Address address = addressService.checkAddressPresent(saveOrderRequest.getAddressId().toString());
            List<Address> addresses = customerAuthEntity.getCustomer().getAddresses();
            boolean match = false;
            for (Address ent : addresses) {
                if (ent.getUuid().equals(address.getUuid())) {
                    match = true;
                }
            }

            if (!match) {
                throw new AuthorizationFailedException("ATHR-004", "You are not authorized to view/update/delete any one else's address");
            }

            //check for payment uuid
            paymentService.checkPayment(saveOrderRequest.getPaymentId());
            //check for restaurant id
            restaurantService.getRestaurantByRestaurantId(saveOrderRequest.getRestaurantId().toString());

        } catch (AddressNotFoundException e) {
            return getResponseEntity(e.getCode(), e.getErrorMessage());
        } catch (AuthorizationFailedException e) {
            return getResponseEntity(e.getCode(), e.getErrorMessage());
        } catch (PaymentMethodNotFoundException e) {
            return getResponseEntity(e.getCode(), e.getErrorMessage());
        } catch (RestaurantNotFoundException e) {
            return getResponseEntity(e.getCode(), e.getErrorMessage());
        }

        Orders savedOrder = new Orders();
        savedOrder.getAddress().setUuid(saveOrderRequest.getAddressId());
        savedOrder.setBill(saveOrderRequest.getBill());
        savedOrder.getCoupon().setUuid(saveOrderRequest.getCouponId().toString());
        savedOrder.setDiscount(saveOrderRequest.getDiscount());
        savedOrder.getPayment().setUuid(saveOrderRequest.getPaymentId().toString());
        savedOrder.getRestaurant().setUuid(saveOrderRequest.getRestaurantId().toString());

        List<ItemQuantity> itemQuantities = saveOrderRequest.getItemQuantities();
        List<ItemEntity> itemEntityList = new ArrayList<>();
        for (ItemQuantity item : itemQuantities) {
            ItemEntity entity = new ItemEntity();
            entity.setUuid(item.getItemId().toString());
            entity.setPrice(item.getPrice());
            //item.getQuantity();
            itemEntityList.add(entity);
        }
        savedOrder.setItems(itemEntityList);


        savedOrder = orderService.saveOrder(savedOrder);

        SaveOrderResponse response = new SaveOrderResponse().id(savedOrder.getUuid()).status("ORDER SUCCESSFULLY PLACED");
        return new ResponseEntity(response, HttpStatus.OK);
    }


    private void populateOrderData(List<Orders> ordersList, List<OrderList> listOfOrderForResponse) {
        for (Orders orderObj : ordersList) {
            OrderList orderList = new OrderList();
            Address address = orderObj.getAddress();
            OrderListAddress orderListAddress = new OrderListAddress();
            orderListAddress.setCity(address.getCity());
            orderListAddress.setFlatBuildingName(address.getFlatBillNumber());
            orderListAddress.setId(UUID.fromString(address.getUuid()));
            orderListAddress.setLocality(address.getLocality());
            orderListAddress.setPincode(address.getPinCode());
            OrderListAddressState state = new OrderListAddressState();
            state.setId(UUID.fromString(address.getStateEntity().getUuid()));
            state.setStateName(address.getStateEntity().getStateName());
            orderListAddress.setState(state);
            orderList.setAddress(orderListAddress);

            OrderListCustomer customer = new OrderListCustomer();
            customer.setId(UUID.fromString(orderObj.getCustomer().getUuid()));
            customer.setContactNumber(orderObj.getCustomer().getContactNumber());
            customer.setEmailAddress(orderObj.getCustomer().getEmail());
            customer.setFirstName(orderObj.getCustomer().getFirstName());
            customer.setLastName(orderObj.getCustomer().getLastName());
            orderList.setCustomer(customer);

            OrderListCoupon coupon = new OrderListCoupon();
            coupon.setId(UUID.fromString(orderObj.getCoupon().getUuid()));
            coupon.setCouponName(orderObj.getCoupon().getCouponName());
            coupon.setPercent(orderObj.getCoupon().getPercent());
            orderList.setCoupon(coupon);

            OrderListPayment payment = new OrderListPayment();
            payment.setId(UUID.fromString(orderObj.getPayment().getUuid()));
            payment.setPaymentName(orderObj.getPayment().getPaymentName());
            orderList.setPayment(payment);

            orderList.setId(UUID.fromString(orderObj.getUuid()));
            orderList.setBill(orderObj.getBill());
            orderList.setDate(orderObj.getDate().toString());


            List<ItemEntity> items = orderObj.getItems();
            List<ItemQuantityResponse> itemList = new ArrayList<>();
            for (ItemEntity entity : items) {
                ItemQuantityResponse response = new ItemQuantityResponse();
                ItemQuantityResponseItem item = new ItemQuantityResponseItem();
                item.setId(UUID.fromString(entity.getUuid()));
                item.setItemName(entity.getItemName());
                item.setItemPrice(entity.getPrice());
                item.setType(entity.getType().equalsIgnoreCase("0") ? ItemQuantityResponseItem.TypeEnum.VEG : ItemQuantityResponseItem.TypeEnum.NON_VEG);
                response.setItem(item);
                response.setPrice(entity.getPrice());
                itemList.add(response);
            }
            orderList.setItemQuantities(itemList);
            listOfOrderForResponse.add(orderList);

        }
    }


    private ResponseEntity getResponseEntity(String code, String errorMessage) {
        ErrorResponse response = new ErrorResponse().code(code).message(errorMessage);
        return new ResponseEntity(response, HttpStatus.FORBIDDEN);
    }


}
