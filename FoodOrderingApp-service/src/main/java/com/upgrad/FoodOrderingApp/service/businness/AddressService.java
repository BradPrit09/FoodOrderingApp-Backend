package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.entity.Address;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddress;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import com.upgrad.FoodOrderingApp.service.util.PinCodeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CustomerAddressDao customerAddressDao;

    /**
     * Method used for saving address to database
     *
     * @param address addressentity object
     * @return Address stored
     * @throws SaveAddressException exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Address saveAddress(Address address) throws SaveAddressException {
        //Validate the pincode
        PinCodeValidator validator = new PinCodeValidator();
        boolean validPinCode = validator.validate(address.getPinCode());
        if (validPinCode) {
            address = addressDao.saveAddress(address);
        } else {
            throw new SaveAddressException("SAR-002", "Invalid pincode");
        }
        return address;
    }

    /**
     * Method used for checking if any field is empty.
     *
     * @param address address Entity object
     * @return true if any field is empty else false
     * @throws SaveAddressException
     */

    public boolean anyFieldEmpty(Address address) throws SaveAddressException {
        boolean empty = false;
        if (address.getCity().trim().isEmpty()
                || address.getFlatBillNumber().trim().isEmpty()
                || address.getLocality().trim().isEmpty()
                || address.getPinCode().trim().isEmpty()
                || address.getStateEntity().getUuid().trim().isEmpty()
        ) {
            throw new SaveAddressException("SAR-001", "No field can be empty");
        } else {
            return empty;
        }

    }

    /**
     * Method used for getting all addresses for a customer
     *
     * @param customerEntity Customer object
     * @return List of AddresseEntity objects
     */

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Address> getAllAddress(CustomerEntity customerEntity) {
        List<Address> addressList = addressDao.getAllAddresses(customerEntity);
        return addressList;
    }

    /**
     * Method to save customer and address relation in Customer and address table
     *
     * @param address  address Entity object
     * @param customer customer Entity object
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCustomerAddress(Address address, CustomerEntity customer) {
        CustomerAddress custAddress = new CustomerAddress();
        custAddress.setAddress(address);
        custAddress.setCustomer(customer);
        customerAddressDao.saveCustomerAddress(custAddress);
    }

    /**
     * Method used for checking if the address is present in db
     *
     * @param addressId address uuid
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Address checkAddressPresent(String addressId) throws AddressNotFoundException {
        Address address = addressDao.checkAddressPresent(addressId);
        if (address == null) {
            throw new AddressNotFoundException("ANF-003", "No address by this id");
        } else {
            return address;
        }
    }

    /**
     * Method used for deleting address from database
     *
     * @param deleteAddress address to be deleted
     */
    public void deleteAddress(Address deleteAddress) {
        addressDao.deleteAddress(deleteAddress);
    }
}
