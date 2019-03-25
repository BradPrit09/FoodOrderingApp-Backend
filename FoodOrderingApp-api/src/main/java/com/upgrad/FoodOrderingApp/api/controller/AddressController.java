package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerAddressService;
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

    private static final String ADDRESS_DELETED_SUCCESSFULLY = "ADDRESS DELETED SUCCESSFULLY";
    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private StateService stateService;

    @Autowired
    CustomerAddressService customerAddressService;

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
                //Save the relation of customer and address in Customer and address table as well
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
     * Rest Endpoint method implementation used for deleting address by address id.
     * Only logged-in user who is owner of the address is allowed to delete a address
     *
     */
    /**
     * @param addressId   address id in string
     * @param accessToken accesstoken for the customer
     * @return Delete response with appropriate ahhtp status
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/address/{address_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeleteAddressResponse> addressDelete(@PathVariable("address_id") final String addressId, @RequestHeader("authorization") final String accessToken) {
        DeleteAddressResponse response = null;
        CustomerEntity customerEntity = null;
        Address address = null;

        //Check if customer is authenticated for the operation
        try {
            CustomerAuthEntity customerAuthEntity = customerService.getCustomerAuthEntity(accessToken);
            customerEntity = customerAuthEntity.getCustomer();
        } catch (AuthorizationFailedException e) {
            response = new DeleteAddressResponse().status(e.getCode() + " " + e.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        //Check whether the addressid field provided is empty
        try {
            checkAddresFiledforEmptiness(addressId);
        } catch (AddressNotFoundException e) {
            response = new DeleteAddressResponse().status(e.getCode() + " " + e.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        //Check if address is present in db
        try {
            address = addressService.checkAddressPresent(addressId);
        } catch (AddressNotFoundException e) {
            response = new DeleteAddressResponse().status(e.getCode() + " " + e.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }


        /**
         * This block checks if there is a proper relationship between address and customer.
         * Only then can the customer be allowed to delete the address details.
         */

        address.setUuid(addressId);
        boolean isCustomerOwnerOfAddress = false;
        try {
            isCustomerOwnerOfAddress = customerAddressService.checkCustomerisOwnerOfAddress(address, customerEntity);
        } catch (AuthorizationFailedException e) {
            response = new DeleteAddressResponse().status(e.getCode() + " " + e.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        //if all validations are successful then go for deletion of the address
        if (isCustomerOwnerOfAddress) {
            addressService.deleteAddress(address);
        }
        response = new DeleteAddressResponse().status(ADDRESS_DELETED_SUCCESSFULLY).id(UUID.fromString(address.getUuid()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Rest Endpoint method implementation used for signing up customer with all details.
     *
     * @return ResponseEntity containing user response
     * @throws SignUpRestrictedException exception thrown in case username of email id are same.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/states", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StatesListResponse> getAllStates() {
        List<StateEntity> stateList = addressService.getAllStates();
        List<StatesList> responseStateList = converttoStateList(stateList);
        StatesListResponse response = new StatesListResponse().states(responseStateList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Method used for converting StateEntity list to StateList for ending it to response
     *
     * @param stateList list of state
     * @return List of StateList
     */
    private List<StatesList> converttoStateList(List<StateEntity> stateList) {
        List<StatesList> stateValueList = new ArrayList<>();
        for (StateEntity entityObject : stateList) {
            StatesList statesListObj = new StatesList();
            statesListObj.setId(UUID.fromString(entityObject.getUuid()));
            statesListObj.setStateName(entityObject.getStateName());
            stateValueList.add(statesListObj);
        }
        return stateValueList;
    }


    /**
     * Method used for checking if the addressid field sent from the client is empty
     * If yes throw exception
     *
     * @param addressId uuid field
     * @throws AddressNotFoundException exception
     */
    private void checkAddresFiledforEmptiness(String addressId) throws AddressNotFoundException {
        if (addressId.trim().isEmpty()) {
            throw new AddressNotFoundException("ANF-005", "Address id can not be empty");
        }
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
