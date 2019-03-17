package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import com.upgrad.FoodOrderingApp.service.util.EmailValidator;
import com.upgrad.FoodOrderingApp.service.util.PasswordValidator;
import com.upgrad.FoodOrderingApp.service.util.PhoneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class CustomerService {

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    @Autowired
    CustomerDao customerDao;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Service method to save customer data in database.
     *
     * @param customerEntity customer entity data to be stored.
     * @return saved customer entity data
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) throws SignUpRestrictedException {
        CustomerEntity contactumberExists = customerDao.checkContactNumber(customerEntity.getContactNumber());
        if (contactumberExists != null && contactumberExists.getContactNumber().equals(customerEntity.getContactNumber())) {
            throw new SignUpRestrictedException("SGR-001", "This contact number is already registered! Try other contact number.");
        }

        //validate password
        validatePassword(customerEntity);
        //add encrypted password and salt values
        String[] encryptedText = cryptographyProvider.encrypt(customerEntity.getPassword());
        customerEntity.setSalt(encryptedText[0]);
        customerEntity.setPassword(encryptedText[1]);
        //validate all fields are filled other than lastname before sending it to database.
        validateFields(customerEntity);
        //validate email id
        validateEmailid(customerEntity);
        //validate phone Number
        validatePhoneNumber(customerEntity);
        return customerDao.createUpdateUser(customerEntity);
    }


    /**
     * Helper method to validate password
     *
     * @param customerEntity entity object with password
     * @throws SignUpRestrictedException exception
     */
    private void validatePassword(CustomerEntity customerEntity) throws SignUpRestrictedException {
        PasswordValidator validator = new PasswordValidator();
        boolean validate = validator.validate(customerEntity.getPassword());
        if (!validate) {
            throw new SignUpRestrictedException("SGR-004", "Weak password!");
        }
    }

    /**
     * Helper method to validate phone Number
     *
     * @param customerEntity customerEntity with phone number
     */
    private void validatePhoneNumber(CustomerEntity customerEntity) throws SignUpRestrictedException {
        PhoneValidator phoneValidator = new PhoneValidator();
        boolean validPhonNumbere = phoneValidator.validate(customerEntity.getContactNumber());
        if (!validPhonNumbere) {
            throw new SignUpRestrictedException("SGR-003", "Invalid contact number!");
        }
    }

    /**
     * Helper method to validate email id
     *
     * @param customerEntity Customer object
     */
    private void validateEmailid(CustomerEntity customerEntity) throws SignUpRestrictedException {
        EmailValidator emailValidator = new EmailValidator();
        boolean validEmailId = emailValidator.validate(customerEntity.getEmail());
        if (!validEmailId) {
            throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
        }
    }

    /**
     * Helper method to validate all fields in CustomerEntity are filled except lastName as that is allowed.
     *
     * @param customerEntity entiry object
     * @throws SignUpRestrictedException exception
     */
    private void validateFields(CustomerEntity customerEntity) throws SignUpRestrictedException {
        if (customerEntity.getUuid() != null && customerEntity.getUuid().trim().isEmpty() ||
                customerEntity.getContactNumber() != null && customerEntity.getContactNumber().trim().isEmpty() ||
                customerEntity.getEmail() != null && customerEntity.getEmail().trim().isEmpty() ||
                customerEntity.getFirstName() != null && customerEntity.getFirstName().trim().isEmpty() ||
                customerEntity.getPassword() != null && customerEntity.getPassword().trim().isEmpty()) {
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled");
        }
    }

    public Object getCustomer(String database_accesstoken2) {
        return null;
    }

    /**
     * method used for authenticating the customer credentials.
     *
     * @param contactNumber contact number of the customer
     * @param password      password of the customer
     * @return CutomerAuthEntity with the auth token
     * @throws AuthenticationFailedException exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity authenticate(String contactNumber, String password) throws AuthenticationFailedException {
        CustomerEntity contactumberExists = customerDao.checkContactNumber(contactNumber);
        if (contactumberExists == null) {
            throw new AuthenticationFailedException("ATH-001", "This contact number has not been registered!");
        }
        CustomerEntity passwordRight = customerDao.checkPasswordisCorrect(contactNumber, password);

        String encryptedPwd = PasswordCryptographyProvider.encrypt(password, contactumberExists.getSalt());
        if (encryptedPwd.equals(contactumberExists.getPassword())) {
            JwtTokenProvider tokenProvider = new JwtTokenProvider(encryptedPwd);
            CustomerAuthEntity authEntity = new CustomerAuthEntity();
            authEntity.setCustomer(contactumberExists);
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            authEntity.setAccessToken(tokenProvider.generateToken(contactumberExists.getUuid(), now, expiresAt));
            authEntity.setLoginAt(now);
            authEntity.setExpiresAt(expiresAt);
            authEntity.setUuid(contactumberExists.getUuid());
            customerDao.createAuthToken(authEntity);
            authEntity.setLogoutAt(null);
            return authEntity;
        } else {
            throw new AuthenticationFailedException("(ATH-002", "Invalid Credentials");
        }
    }


    /**
     * method used by customer to log out from the application.
     *
     * @param accessToken accestoken through which used has logged in
     * @return logout customerEntiry object
     * @throws AuthorizationFailedException exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity logout(String accessToken) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerByAccessToken(accessToken);
        if (customerAuthEntity == null) {
            //if access token does not exist then throw ATHR-001
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        } else if (customerAuthEntity.getLogoutAt() != null) {
            //if customer with this accestoken has already logged out then throw ATHR-002
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        } else if (ZonedDateTime.now().isAfter(customerAuthEntity.getExpiresAt())) {
            //if expiry date of this token is already past the current date then throw ATHR-003
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
        } else {
            //update the logout date for this accesstoken
            final ZonedDateTime logoutAtDate = ZonedDateTime.now();
            customerAuthEntity.setLogoutAt(logoutAtDate);
            customerDao.updateLogOutDate(customerAuthEntity);
            return customerAuthEntity.getCustomer();
        }
    }

    /**
     * method used for updating customer details into database.
     *
     * @param customerEntity entity object
     * @throws AuthorizationFailedException exception for authorization
     * @throws UpdateCustomerException      exception for updating customer details.
     */

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity updateCustomerDetails(CustomerEntity customerEntity) throws AuthorizationFailedException, UpdateCustomerException {
        //CustomerAuthEntity customerAuthEntity = getCustomerAuthEntity(accessToken);

        if (customerEntity.getFirstName().trim().isEmpty()) {
            throw new UpdateCustomerException("UCR-002", "First name field should not be empty");
        }

        customerEntity.setFirstName(customerEntity.getFirstName());
        customerEntity.setLastName(customerEntity.getLastName());
        customerEntity = customerDao.createUpdateUser(customerEntity);
        return customerEntity;
    }


    /**
     * method used for updating the password of a customer.
     * It takes the oldPassword, newpassword, authenticates customer through accesstoken and
     * returns back the updated customer entity object
     *
     * @param entity      customerEntity object
     * @param oldPassword oldpassword in string
     * @param newPassword newpasword in string
     * @return updated customer entity object
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity updateCustomerPassword(String oldPassword, String newPassword, CustomerEntity entity) throws UpdateCustomerException, AuthorizationFailedException {
        if (oldPassword.trim().isEmpty() || newPassword.trim().isEmpty()) {
            throw new UpdateCustomerException("UCR-003", "No field should be empty");
        }
        //check if the new password is good for savings considering the restrictions.
        PasswordValidator validator = new PasswordValidator();
        boolean validate = validator.validate(newPassword);
        if (validate) {
            //This implies new password is ok for updating in database. before that make sure old password provided is right
            String encryptedPwd = PasswordCryptographyProvider.encrypt(oldPassword, entity.getSalt());
            if (encryptedPwd.equals(entity.getPassword())) {
                //create encrypted password to be stored on database.
                String newEncryptPaswd = PasswordCryptographyProvider.encrypt(newPassword, entity.getSalt());
                entity.setPassword(newEncryptPaswd);
                customerDao.createUpdateUser(entity);
            } else {
                throw new UpdateCustomerException("UCR-004", "Incorrect old password!");
            }
        } else {
            throw new UpdateCustomerException("UCR-001", "Weak password!");
        }
        return entity;
    }

    /**
     * helper method to check the authentication of user through accesstoken
     *
     * @param accessToken token of the customer
     * @return CustomerAuthentity object
     * @throws AuthorizationFailedException exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity getCustomerAuthEntity(String accessToken) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerByAccessToken(accessToken);
        if (customerAuthEntity == null) {
            //if access token does not exist then throw ATHR-001
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        } else if (customerAuthEntity.getLogoutAt() != null) {
            //if customer with this accestoken has already logged out then throw ATHR-002
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        } else if (ZonedDateTime.now().isAfter(customerAuthEntity.getExpiresAt())) {
            //if expiry date of this token is already past the current date then throw ATHR-003
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
        }
        return customerAuthEntity;
    }


}
