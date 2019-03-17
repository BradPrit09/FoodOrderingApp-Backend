package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.Address;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddress;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for providing customer related database trasactions.
 */
@Repository
public class AddressDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private CustomerAddressDao customerAddressDao;



    /**
     * Method used for saving address
     *
     * @param address address entity to be persisted
     * @return saved address
     */
    public Address saveAddress(Address address) {
        entityManager.persist(address);
        return address;
    }

    /**
     * Method used for getting all addresses pertaining to a customer.
     *
     * @param customerEntity entity object of customer
     * @return list of address entity object
     */
    public List<Address> getAllAddresses(CustomerEntity customerEntity) {
        List<CustomerAddress> allAddresses = customerAddressDao.getAllAddresses(customerEntity);
        List<Address> addressList = new ArrayList<Address>();
        for (CustomerAddress object : allAddresses) {
            addressList.add(object.getAddress());
        }
        return addressList;
    }
}
