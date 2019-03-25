package com.upgrad.FoodOrderingApp.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.ItemQuantity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SaveOrderRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-25T15:30:38.403+05:30")

public class SaveOrderRequest   {
  @JsonProperty("address_id")
  private String addressId = null;

  @JsonProperty("payment_id")
  private UUID paymentId = null;

  @JsonProperty("bill")
  private BigDecimal bill = null;

  @JsonProperty("discount")
  private BigDecimal discount = null;

  @JsonProperty("coupon_id")
  private UUID couponId = null;

  @JsonProperty("restaurant_id")
  private UUID restaurantId = null;

  @JsonProperty("item_quantities")
  @Valid
  private List<ItemQuantity> itemQuantities = null;

  public SaveOrderRequest addressId(String addressId) {
    this.addressId = addressId;
    return this;
  }

  /**
   * uuid of the address
   * @return addressId
  **/
  @ApiModelProperty(required = true, value = "uuid of the address")
  @NotNull


  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public SaveOrderRequest paymentId(UUID paymentId) {
    this.paymentId = paymentId;
    return this;
  }

  /**
   * uuid of the payment
   * @return paymentId
  **/
  @ApiModelProperty(required = true, value = "uuid of the payment")
  @NotNull

  @Valid

  public UUID getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(UUID paymentId) {
    this.paymentId = paymentId;
  }

  public SaveOrderRequest bill(BigDecimal bill) {
    this.bill = bill;
    return this;
  }

  /**
   * order bill
   * @return bill
  **/
  @ApiModelProperty(required = true, value = "order bill")
  @NotNull

  @Valid

  public BigDecimal getBill() {
    return bill;
  }

  public void setBill(BigDecimal bill) {
    this.bill = bill;
  }

  public SaveOrderRequest discount(BigDecimal discount) {
    this.discount = discount;
    return this;
  }

  /**
   * order discount
   * @return discount
  **/
  @ApiModelProperty(value = "order discount")

  @Valid

  public BigDecimal getDiscount() {
    return discount;
  }

  public void setDiscount(BigDecimal discount) {
    this.discount = discount;
  }

  public SaveOrderRequest couponId(UUID couponId) {
    this.couponId = couponId;
    return this;
  }

  /**
   * uuid of the coupon
   * @return couponId
  **/
  @ApiModelProperty(value = "uuid of the coupon")

  @Valid

  public UUID getCouponId() {
    return couponId;
  }

  public void setCouponId(UUID couponId) {
    this.couponId = couponId;
  }

  public SaveOrderRequest restaurantId(UUID restaurantId) {
    this.restaurantId = restaurantId;
    return this;
  }

  /**
   * uuid of the restaurant
   * @return restaurantId
  **/
  @ApiModelProperty(required = true, value = "uuid of the restaurant")
  @NotNull

  @Valid

  public UUID getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(UUID restaurantId) {
    this.restaurantId = restaurantId;
  }

  public SaveOrderRequest itemQuantities(List<ItemQuantity> itemQuantities) {
    this.itemQuantities = itemQuantities;
    return this;
  }

  public SaveOrderRequest addItemQuantitiesItem(ItemQuantity itemQuantitiesItem) {
    if (this.itemQuantities == null) {
      this.itemQuantities = new ArrayList<>();
    }
    this.itemQuantities.add(itemQuantitiesItem);
    return this;
  }

  /**
   * List of item quantities.
   * @return itemQuantities
  **/
  @ApiModelProperty(value = "List of item quantities.")

  @Valid

  public List<ItemQuantity> getItemQuantities() {
    return itemQuantities;
  }

  public void setItemQuantities(List<ItemQuantity> itemQuantities) {
    this.itemQuantities = itemQuantities;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SaveOrderRequest saveOrderRequest = (SaveOrderRequest) o;
    return Objects.equals(this.addressId, saveOrderRequest.addressId) &&
        Objects.equals(this.paymentId, saveOrderRequest.paymentId) &&
        Objects.equals(this.bill, saveOrderRequest.bill) &&
        Objects.equals(this.discount, saveOrderRequest.discount) &&
        Objects.equals(this.couponId, saveOrderRequest.couponId) &&
        Objects.equals(this.restaurantId, saveOrderRequest.restaurantId) &&
        Objects.equals(this.itemQuantities, saveOrderRequest.itemQuantities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressId, paymentId, bill, discount, couponId, restaurantId, itemQuantities);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SaveOrderRequest {\n");
    
    sb.append("    addressId: ").append(toIndentedString(addressId)).append("\n");
    sb.append("    paymentId: ").append(toIndentedString(paymentId)).append("\n");
    sb.append("    bill: ").append(toIndentedString(bill)).append("\n");
    sb.append("    discount: ").append(toIndentedString(discount)).append("\n");
    sb.append("    couponId: ").append(toIndentedString(couponId)).append("\n");
    sb.append("    restaurantId: ").append(toIndentedString(restaurantId)).append("\n");
    sb.append("    itemQuantities: ").append(toIndentedString(itemQuantities)).append("\n");
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

