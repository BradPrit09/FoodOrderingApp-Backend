package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.entity.Address;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddress;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerAddressService {

    @Autowired
    private CustomerAddressDao customerAddressDao;


    /**
     * Method used for checking if the customer uuid and address uuid have a mapping in the customerAddress table.
     * If yes then the customer is the owner of the address
     *
     * @param addressId      address instance with uuid in it
     * @param customerEntity customer instance with uuid in it
     * @return whether their is valid relationship. true of yes , false if no
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean checkCustomerisOwnerOfAddress(Address addressId, CustomerEntity customerEntity) throws AuthorizationFailedException {
        boolean isValid = false;
        CustomerAddress valueObject = customerAddressDao.checkCustomerisOwnerOfAddress(addressId.getUuid(), customerEntity.getUuid());
        if (valueObject != null) {
            addressId = valueObject.getAddress();
            isValid = true;
        } else {
            throw new AuthorizationFailedException("ATHR-004", "You are not authorized to view/update/delete any one else's address");
        }
        return isValid;
    }
}
