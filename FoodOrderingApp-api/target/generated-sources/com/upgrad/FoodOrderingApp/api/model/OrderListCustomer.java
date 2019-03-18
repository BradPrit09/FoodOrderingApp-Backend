package com.upgrad.FoodOrderingApp.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OrderListCustomer
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-18T08:42:25.757+05:30")

public class OrderListCustomer   {
  @JsonProperty("id")
  private UUID id = null;

  @JsonProperty("first_name")
  private String firstName = null;

  @JsonProperty("last_name")
  private String lastName = null;

  @JsonProperty("email_address")
  private String emailAddress = null;

  @JsonProperty("contact_number")
  private String contactNumber = null;

  public OrderListCustomer id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the customer in a standard UUID format
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier of the customer in a standard UUID format")

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public OrderListCustomer firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * First name of the new customer
   * @return firstName
  **/
  @ApiModelProperty(value = "First name of the new customer")


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public OrderListCustomer lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Last name of the new customer
   * @return lastName
  **/
  @ApiModelProperty(value = "Last name of the new customer")


  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public OrderListCustomer emailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  /**
   * Email address of the new customer
   * @return emailAddress
  **/
  @ApiModelProperty(value = "Email address of the new customer")


  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public OrderListCustomer contactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
    return this;
  }

  /**
   * Contact Number of the new customer
   * @return contactNumber
  **/
  @ApiModelProperty(value = "Contact Number of the new customer")


  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderListCustomer orderListCustomer = (OrderListCustomer) o;
    return Objects.equals(this.id, orderListCustomer.id) &&
        Objects.equals(this.firstName, orderListCustomer.firstName) &&
        Objects.equals(this.lastName, orderListCustomer.lastName) &&
        Objects.equals(this.emailAddress, orderListCustomer.emailAddress) &&
        Objects.equals(this.contactNumber, orderListCustomer.contactNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, emailAddress, contactNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderListCustomer {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    emailAddress: ").append(toIndentedString(emailAddress)).append("\n");
    sb.append("    contactNumber: ").append(toIndentedString(contactNumber)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

