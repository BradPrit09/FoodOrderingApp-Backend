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
    private static final String CHECK_CUSTOMER_ADDRESS = "checkCustomerAddress";
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


    /**
     * Method used for checking if the customer uuid and address uuid have a mapping in the customerAddress table.
     * If yes then the customer is the owner of the address
     *
     * @param addressUUID  address uuid
     * @param customerUUID customer uuid
     * @return CustomerAddress instance
     */
    public CustomerAddress checkCustomerisOwnerOfAddress(String addressUUID, String customerUUID) {
        try {
            return entityManager.createNamedQuery(CHECK_CUSTOMER_ADDRESS, CustomerAddress.class)
                    .setParameter("customeruuid", customerUUID)
                    .setParameter("addressuuid", addressUUID)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
