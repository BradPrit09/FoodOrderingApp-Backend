package com.upgrad.FoodOrderingApp.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UpdateCustomerResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-18T01:17:54.193+05:30")

public class UpdateCustomerResponse   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("first_name")
  private String firstName = null;

  @JsonProperty("last_name")
  private String lastName = null;

  public UpdateCustomerResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * uuid of the customer with updated details
   * @return id
  **/
  @ApiModelProperty(required = true, value = "uuid of the customer with updated details")
  @NotNull


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UpdateCustomerResponse status(String status) {
    this.status = status;
    return this;
  }

  /**
   * message showing the status of the customer
   * @return status
  **/
  @ApiModelProperty(required = true, value = "message showing the status of the customer")
  @NotNull


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public UpdateCustomerResponse firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * First name of the new customer
   * @return firstName
  **/
  @ApiModelProperty(required = true, value = "First name of the new customer")
  @NotNull


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public UpdateCustomerResponse lastName(String lastName) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateCustomerResponse updateCustomerResponse = (UpdateCustomerResponse) o;
    return Objects.equals(this.id, updateCustomerResponse.id) &&
        Objects.equals(this.status, updateCustomerResponse.status) &&
        Objects.equals(this.firstName, updateCustomerResponse.firstName) &&
        Objects.equals(this.lastName, updateCustomerResponse.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status, firstName, lastName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateCustomerResponse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
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

