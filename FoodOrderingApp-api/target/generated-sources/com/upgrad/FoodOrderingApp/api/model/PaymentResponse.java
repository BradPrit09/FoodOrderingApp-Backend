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
 * PaymentResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-18T01:17:56.548+05:30")

public class PaymentResponse   {
  @JsonProperty("id")
  private UUID id = null;

  @JsonProperty("payment_name")
  private String paymentName = null;

  public PaymentResponse id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the payment method in a standard UUID format
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier of the payment method in a standard UUID format")

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public PaymentResponse paymentName(String paymentName) {
    this.paymentName = paymentName;
    return this;
  }

  /**
   * Name of the payment method
   * @return paymentName
  **/
  @ApiModelProperty(value = "Name of the payment method")


  public String getPaymentName() {
    return paymentName;
  }

  public void setPaymentName(String paymentName) {
    this.paymentName = paymentName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentResponse paymentResponse = (PaymentResponse) o;
    return Objects.equals(this.id, paymentResponse.id) &&
        Objects.equals(this.paymentName, paymentResponse.paymentName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, paymentName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentResponse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    paymentName: ").append(toIndentedString(paymentName)).append("\n");
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

