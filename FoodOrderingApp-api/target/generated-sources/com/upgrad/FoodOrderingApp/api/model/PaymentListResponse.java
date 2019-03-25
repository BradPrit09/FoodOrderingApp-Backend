package com.upgrad.FoodOrderingApp.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.PaymentResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PaymentListResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-25T15:30:38.669+05:30")

public class PaymentListResponse   {
  @JsonProperty("paymentMethods")
  @Valid
  private List<PaymentResponse> paymentMethods = null;

  public PaymentListResponse paymentMethods(List<PaymentResponse> paymentMethods) {
    this.paymentMethods = paymentMethods;
    return this;
  }

  public PaymentListResponse addPaymentMethodsItem(PaymentResponse paymentMethodsItem) {
    if (this.paymentMethods == null) {
      this.paymentMethods = new ArrayList<>();
    }
    this.paymentMethods.add(paymentMethodsItem);
    return this;
  }

  /**
   * List of payment methods
   * @return paymentMethods
  **/
  @ApiModelProperty(value = "List of payment methods")

  @Valid

  public List<PaymentResponse> getPaymentMethods() {
    return paymentMethods;
  }

  public void setPaymentMethods(List<PaymentResponse> paymentMethods) {
    this.paymentMethods = paymentMethods;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentListResponse paymentListResponse = (PaymentListResponse) o;
    return Objects.equals(this.paymentMethods, paymentListResponse.paymentMethods);
  }

  @Override
  public int hashCode() {
    return Objects.hash(paymentMethods);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentListResponse {\n");
    
    sb.append("    paymentMethods: ").append(toIndentedString(paymentMethods)).append("\n");
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

