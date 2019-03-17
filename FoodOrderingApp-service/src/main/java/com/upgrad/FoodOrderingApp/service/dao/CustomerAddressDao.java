package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAddress;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Dao class for performing operations related to CustomerAddress table
 */
@Repository
public class CustomerAddressDao {

    private static final String ADDRESS_FOR_CUSTOMER = "addressForCustomer";
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Method used for saving customer and address data
     *
     * @param customerAddress Customeraddress entity
     * @return Customeraddress object persisted in db
     */
    public CustomerAddress saveCustomerAddress(CustomerAddress customerAddress) {
        entityManager.persist(customerAddress);
        return customerAddress;
    }

    /**
     * Method used for getting all the address for a Customer
     *
     * @param customerEntity Customer Entity object
     * @return List of CustomerAddress objects
     */
    public List<CustomerAddress> getAllAddresses(CustomerEntity customerEntity) {
        try {
            return entityManager.createNamedQuery(ADDRESS_FOR_CUSTOMER, CustomerAddress.class).setParameter("uuid", customerEntity.getUuid()).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }


}
