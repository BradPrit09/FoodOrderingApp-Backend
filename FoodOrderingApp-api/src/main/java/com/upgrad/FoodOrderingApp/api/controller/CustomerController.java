package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.UUID;


/**
 * This class exposes rest apis for customer related operations.
 */
@RestController
@CrossOrigin
@RequestMapping("/")
public class CustomerController {

    private static final String CUSTOMER_SUCCESSFULLY_REGISTERED = "CUSTOMER SUCCESSFULLY REGISTERED";
    private static final String SIGNIN_MESSAGE = "SIGNED IN SUCCESSFULLY";
    private static final String SIGNED_OUT_SUCCESSFULLY = "SIGNED OUT SUCCESSFULLY";
    private static final String CUSTOMER_DETAILS_UPDATED_SUCCESSFULLY = "CUSTOMER DETAILS UPDATED SUCCESSFULLY";
    private static final String CUSTOMER_PASSWORD_UPDATED_SUCCESSFULLY = "CUSTOMER PASSWORD UPDATED SUCCESSFULLY";


    @Autowired
    private CustomerService customerService;


    /**
     * Rest Endpoint method implementation used for signing up customer with all details.
     *
     * @param signupUserRequest request object containing user details.
     * @return ResponseEntity containing user response
     * @throws SignUpRestrictedException exception thrown in case username of email id are same.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/customer/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerRequest> signUp(final SignupCustomerRequest signupUserRequest) throws SignUpRestrictedException {
        //Set the customer entity object
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setUuid(UUID.randomUUID().toString());
        customerEntity.setFirstName(signupUserRequest.getFirstName());
        customerEntity.setLastName(signupUserRequest.getLastName());
        customerEntity.setEmail(signupUserRequest.getEmailAddress());
        customerEntity.setContactNumber(signupUserRequest.getContactNumber());
        customerEntity.setPassword(signupUserRequest.getPassword());
        //Pass the customer entity object for persisting in database.
        CustomerEntity createdCustomerEntity = customerService.saveCustomer(customerEntity);
        SignupCustomerResponse customerResponse = new SignupCustomerResponse().id(createdCustomerEntity.getUuid()).status(CUSTOMER_SUCCESSFULLY_REGISTERED);
        return new ResponseEntity(customerResponse, HttpStatus.CREATED);

    }


    /**
     * Rest Endpoint method implementation  used to signin a user into the system.
     * The user is first authenticated with his username and password.
     * Then, user auth token is created and with this auth token user
     * is given access to the application.
     *
     * @param authorization authorization string provided in the format "Basic <BASE64 encoded value>"
     * @return ResponseEntity providing signinresponse object
     * @throws AuthenticationFailedException if user is not authenticated then this exception is thrown
     */

    @RequestMapping(method = RequestMethod.POST, path = "/customer/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> signin(@RequestHeader final String authorization) throws AuthenticationFailedException {
        //TypeResolutionContext.Basic dXNlcm5hbWU6cGFzc3dvcmQ =
        //above is a sample encoded text where the username is "username" and password is "password" separated by a ":"
        byte[] decode = null;
        String decodedText = null;
        String[] decodedArray = null;
        try {
            decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
            decodedText = new String(decode);
            decodedArray = decodedText.split(":");
        } catch (IllegalArgumentException e) {
//            throw new AuthenticationFailedException("ATH-003", "Incorrect format of decoded customer name and password");
            return new ResponseEntity<>(new LoginResponse().id("ATH-003").message("Incorrect format of decoded customer name and password"), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (ArrayIndexOutOfBoundsException aexp) {
            return new ResponseEntity<>(new LoginResponse().id("ATH-003").message("Incorrect format of decoded customer name and password"), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        CustomerAuthEntity custAuthToken = null;
        try {
            custAuthToken = customerService.authenticate(decodedArray[0], decodedArray[1]);
        } catch (AuthenticationFailedException exp) {
            return new ResponseEntity<>(new LoginResponse().id(exp.getCode()).message(exp.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        CustomerEntity user = custAuthToken.getCustomer();
        LoginResponse authorizedCustomerResponse = new LoginResponse().id(user.getUuid()).
                message(SIGNIN_MESSAGE).
                firstName(user.getFirstName()).
                lastName(user.getLastName()).
                emailAddress(user.getEmail()).
                contactNumber(user.getContactNumber());
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", custAuthToken.getAccessToken());
        return new ResponseEntity<>(authorizedCustomerResponse, headers, HttpStatus.OK);
    }


    /**
     * Rest Endpoint method implementation used for signing out user using the access token passed as parameter.
     * If access token is valid or available then SignOutRestrictedException is thrown.
     *
     * @param accessToken accesstoken passed as String
     * @return ResponseEntity object containing SignoutResponse object
     * @throws AuthorizationFailedException exception thrown in case of no acess token found.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/customer/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LogoutResponse> signout(@RequestHeader final String accessToken) throws AuthorizationFailedException {
        LogoutResponse signOutResponse = null;
        LogoutResponse errorResponse = null;
        CustomerEntity userEntity = null;
        try {
            userEntity = customerService.logout(accessToken);
        } catch (AuthorizationFailedException exp) {
            errorResponse = new LogoutResponse().message(exp.getErrorMessage()).id(exp.getCode());
        }
        if (errorResponse != null && !errorResponse.getMessage().trim().isEmpty()) {
            signOutResponse = new LogoutResponse().message(errorResponse.getMessage()).id(errorResponse.getId());
        } else {
            signOutResponse = new LogoutResponse().id(userEntity.getUuid()).message(SIGNED_OUT_SUCCESSFULLY);
        }
        return new ResponseEntity<>(signOutResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdateCustomerResponse> updateCustomer(UpdateCustomerRequest updateCustomerRequest, @RequestHeader final String accessToken) throws AuthorizationFailedException {
        CustomerEntity updatedCustomerEntity = null;
        UpdateCustomerResponse customerResponse = null;
        boolean errorResponse = false;
        try {
            //Set the customer entity object
            CustomerEntity customerEntity = new CustomerEntity();
            //customerEntity.setUuid(UUID.randomUUID().toString());
            customerEntity.setFirstName(updateCustomerRequest.getFirstName());
            customerEntity.setLastName(updateCustomerRequest.getLastName());
            CustomerAuthEntity customerAuthEntity = customerService.getCustomerAuthEntity(accessToken);
            updatedCustomerEntity = customerService.updateCustomerDetails(customerAuthEntity.getCustomer());
        } catch (AuthorizationFailedException exp) {
            customerResponse = new UpdateCustomerResponse().id(exp.getCode()).status(exp.getErrorMessage());
            errorResponse = true;
        } catch (UpdateCustomerException e) {
            customerResponse = new UpdateCustomerResponse().id(e.getCode()).status(e.getErrorMessage());
            errorResponse = true;

        }

        //if errorresponse variable is true send updated specific Customer response
        if (errorResponse) {
            return new ResponseEntity(customerResponse, HttpStatus.FORBIDDEN);
        } else {
            customerResponse = new UpdateCustomerResponse().id(updatedCustomerEntity.getUuid())
                    .firstName(updatedCustomerEntity.getFirstName())
                    .lastName(updatedCustomerEntity.getLastName())
                    .status(CUSTOMER_DETAILS_UPDATED_SUCCESSFULLY);
            return new ResponseEntity(customerResponse, HttpStatus.OK);
        }

    }

    @RequestMapping(method = RequestMethod.PUT, path = "/customer/password", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdatePasswordResponse> updateCustomerPassword(UpdatePasswordRequest updatePasswordRequest, @RequestHeader final String accessToken) {
        CustomerEntity entity = new CustomerEntity();
        UpdatePasswordResponse updatePasswordResponse = null;
        boolean errorResponse = false;

        try {
            CustomerAuthEntity customerAuthEntity = customerService.getCustomerAuthEntity(accessToken);
            entity = customerService.updateCustomerPassword(updatePasswordRequest.getOldPassword(), updatePasswordRequest.getNewPassword(), customerAuthEntity.getCustomer());
        } catch (UpdateCustomerException e) {
            updatePasswordResponse = new UpdatePasswordResponse().id(e.getCode()).status(e.getErrorMessage());
            errorResponse = true;
        } catch (AuthorizationFailedException e) {
            updatePasswordResponse = new UpdatePasswordResponse().id(e.getCode()).status(e.getErrorMessage());
            errorResponse = true;
        }
        if (errorResponse) {
            return new ResponseEntity(updatePasswordResponse, HttpStatus.FORBIDDEN);
        } else {
            updatePasswordResponse = new UpdatePasswordResponse().id(entity.getUuid())
                    .status(CUSTOMER_PASSWORD_UPDATED_SUCCESSFULLY);
            return new ResponseEntity(updatePasswordResponse, HttpStatus.OK);
        }
    }
}
