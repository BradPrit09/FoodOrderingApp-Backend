package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.StateService;
import com.upgrad.FoodOrderingApp.service.entity.Address;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * This class exposes rest apis for customer address related operations.
 */
@RestController
@CrossOrigin
@RequestMapping("/")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private StateService stateService;

    /**
     * Rest Endpoint method implementation used for signing up customer with all details.
     *
     * @param saveAddressRequest request object containing address details.
     * @return ResponseEntity containing user response
     */
    @RequestMapping(method = RequestMethod.POST, path = "/address", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse> saveAddress(final SaveAddressRequest saveAddressRequest, @RequestHeader final String accessToken) {
        SaveAddressResponse response = null;
        Address address = new Address();
        CustomerAuthEntity authEntity = new CustomerAuthEntity();
        try {
            authEntity = customerService.getCustomerAuthEntity(accessToken);
        } catch (AuthorizationFailedException e) {
            response = new SaveAddressResponse().id(e.getCode()).status(e.getErrorMessage());
        }

        try {
            address.setUuid(UUID.randomUUID().toString());
            address.setCity(saveAddressRequest.getCity());
            address.setFlatBillNumber(saveAddressRequest.getFlatBuildingName());
            address.setLocality(saveAddressRequest.getLocality());
            address.setPinCode(saveAddressRequest.getPincode());
            address.setStateEntity(new StateEntity());
            address.getStateEntity().setUuid(saveAddressRequest.getStateUuid());
            if (!addressService.anyFieldEmpty(address)) {
                address.setStateEntity(stateService.getStateByUUID(saveAddressRequest.getStateUuid()));
                //Authenticate the user with access Token
                customerService.getCustomerAuthEntity(accessToken);
                address = addressService.saveAddress(address);
                //Save the relattion of customer and address in Customer and address table as well
                addressService.saveCustomerAddress(address, authEntity.getCustomer());
            }
        } catch (AddressNotFoundException e) {
            response = new SaveAddressResponse().id(e.getCode()).status(e.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } catch (AuthorizationFailedException e) {
            response = new SaveAddressResponse().id(e.getCode()).status(e.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } catch (SaveAddressException e) {
            response = new SaveAddressResponse().id(e.getCode()).status(e.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        response = new SaveAddressResponse().id(address.getUuid()).status("ADDRESS SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SaveAddressResponse>(response, HttpStatus.OK);
    }

    /**
     * Rest Endpoint method implementation used for signing up customer with all details.
     *
     * @return ResponseEntity containing user response
     * @throws SignUpRestrictedException exception thrown in case username of email id are same.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/address/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddressListResponse> getAllAddressForCustomer(@RequestHeader final String accessToken) throws AuthorizationFailedException {
        CustomerEntity customerEntity = null;
        AddressListResponse response = null;

        CustomerAuthEntity customerAuthEntity = customerService.getCustomerAuthEntity(accessToken);
        customerEntity = customerAuthEntity.getCustomer();

        List<Address> addressList = addressService.getAllAddress(customerEntity);
        List<AddressList> listAddress = converttoAddressList(addressList);

        response = new AddressListResponse().addresses(listAddress);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Helper method to convert a Address object to an addressList object list
     *
     * @param addressList Address List
     * @return List of AddressList object
     */
    private List<AddressList> converttoAddressList(List<Address> addressList) {
        List<AddressList> newList = new ArrayList<>();
        for (Address oldObject : addressList) {
            AddressList listObject = new AddressList();
            listObject.setId(UUID.fromString(oldObject.getUuid()));
            listObject.setCity(oldObject.getCity());
            listObject.setFlatBuildingName(oldObject.getFlatBillNumber());
            listObject.setLocality(oldObject.getLocality());
            listObject.setPincode(oldObject.getPinCode());
            AddressListState state = new AddressListState();
            state.setId(UUID.fromString(oldObject.getStateEntity().getUuid()));
            state.setStateName(oldObject.getStateEntity().getStateName());
            listObject.setState(state);
            newList.add(listObject);
        }
        return newList;
    }


}
