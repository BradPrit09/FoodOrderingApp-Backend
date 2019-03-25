package com.upgrad.FoodOrderingApp.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.ItemQuantityResponse;
import com.upgrad.FoodOrderingApp.api.model.OrderListAddress;
import com.upgrad.FoodOrderingApp.api.model.OrderListCoupon;
import com.upgrad.FoodOrderingApp.api.model.OrderListCustomer;
import com.upgrad.FoodOrderingApp.api.model.OrderListPayment;
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
 * OrderList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-25T15:30:38.403+05:30")

public class OrderList   {
  @JsonProperty("id")
  private UUID id = null;

  @JsonProperty("bill")
  private BigDecimal bill = null;

  @JsonProperty("coupon")
  private OrderListCoupon coupon = null;

  @JsonProperty("discount")
  private BigDecimal discount = null;

  @JsonProperty("date")
  private String date = null;

  @JsonProperty("payment")
  private OrderListPayment payment = null;

  @JsonProperty("customer")
  private OrderListCustomer customer = null;

  @JsonProperty("address")
  private OrderListAddress address = null;

  @JsonProperty("item_quantities")
  @Valid
  private List<ItemQuantityResponse> itemQuantities = null;

  public OrderList id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the order in a standard UUID format
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier of the order in a standard UUID format")

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public OrderList bill(BigDecimal bill) {
    this.bill = bill;
    return this;
  }

  /**
   * order bill
   * @return bill
  **/
  @ApiModelProperty(value = "order bill")

  @Valid

  public BigDecimal getBill() {
    return bill;
  }

  public void setBill(BigDecimal bill) {
    this.bill = bill;
  }

  public OrderList coupon(OrderListCoupon coupon) {
    this.coupon = coupon;
    return this;
  }

  /**
   * Get coupon
   * @return coupon
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OrderListCoupon getCoupon() {
    return coupon;
  }

  public void setCoupon(OrderListCoupon coupon) {
    this.coupon = coupon;
  }

  public OrderList discount(BigDecimal discount) {
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

  public OrderList date(String date) {
    this.date = date;
    return this;
  }

  /**
   * Full date-time format (yyyy-MM-dd HH:mm)
   * @return date
  **/
  @ApiModelProperty(value = "Full date-time format (yyyy-MM-dd HH:mm)")


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public OrderList payment(OrderListPayment payment) {
    this.payment = payment;
    return this;
  }

  /**
   * Get payment
   * @return payment
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OrderListPayment getPayment() {
    return payment;
  }

  public void setPayment(OrderListPayment payment) {
    this.payment = payment;
  }

  public OrderList customer(OrderListCustomer customer) {
    this.customer = customer;
    return this;
  }

  /**
   * Get customer
   * @return customer
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OrderListCustomer getCustomer() {
    return customer;
  }

  public void setCustomer(OrderListCustomer customer) {
    this.customer = customer;
  }

  public OrderList address(OrderListAddress address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OrderListAddress getAddress() {
    return address;
  }

  public void setAddress(OrderListAddress address) {
    this.address = address;
  }

  public OrderList itemQuantities(List<ItemQuantityResponse> itemQuantities) {
    this.itemQuantities = itemQuantities;
    return this;
  }

  public OrderList addItemQuantitiesItem(ItemQuantityResponse itemQuantitiesItem) {
    if (this.itemQuantities == null) {
      this.itemQuantities = new ArrayList<>();
    }
    this.itemQuantities.add(itemQuantitiesItem);
    return this;
  }

  /**
   * List of item quantity
   * @return itemQuantities
  **/
  @ApiModelProperty(value = "List of item quantity")

  @Valid

  public List<ItemQuantityResponse> getItemQuantities() {
    return itemQuantities;
  }

  public void setItemQuantities(List<ItemQuantityResponse> itemQuantities) {
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
    OrderList orderList = (OrderList) o;
    return Objects.equals(this.id, orderList.id) &&
        Objects.equals(this.bill, orderList.bill) &&
        Objects.equals(this.coupon, orderList.coupon) &&
        Objects.equals(this.discount, orderList.discount) &&
        Objects.equals(this.date, orderList.date) &&
        Objects.equals(this.payment, orderList.payment) &&
        Objects.equals(this.customer, orderList.customer) &&
        Objects.equals(this.address, orderList.address) &&
        Objects.equals(this.itemQuantities, orderList.itemQuantities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bill, coupon, discount, date, payment, customer, address, itemQuantities);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderList {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    bill: ").append(toIndentedString(bill)).append("\n");
    sb.append("    coupon: ").append(toIndentedString(coupon)).append("\n");
    sb.append("    discount: ").append(toIndentedString(discount)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    payment: ").append(toIndentedString(payment)).append("\n");
    sb.append("    customer: ").append(toIndentedString(customer)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
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

