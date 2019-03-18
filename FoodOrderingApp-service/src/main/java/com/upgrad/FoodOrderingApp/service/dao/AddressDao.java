package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.Address;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddress;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for providing customer related database trasactions.
 */
@Repository
public class AddressDao {

    private static final String CHECK_ADDRESS_AVAILABLE = "checkAddressAvailable";
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
        List<Address> addressList = new ArrayList<>();
        for (CustomerAddress object : allAddresses) {
            addressList.add(object.getAddress());
        }
        return addressList;
    }

    /**
     * Method used for checking if the address is present in the database.
     *
     * @param addressId addressid string
     */
    public Address checkAddressPresent(String addressId) {
        try {
            return entityManager.createNamedQuery(CHECK_ADDRESS_AVAILABLE, Address.class).setParameter("uuid", addressId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Method used for deleteing the address from database
     *
     * @param deleteAddress address to be deleted
     */
    public void deleteAddress(Address deleteAddress) {
        Address address = entityManager.find(Address.class, deleteAddress);
        entityManager.remove(address);
    }
}
