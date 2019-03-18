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
 * OrderListCoupon
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-18T08:42:25.757+05:30")

public class OrderListCoupon   {
  @JsonProperty("id")
  private UUID id = null;

  @JsonProperty("coupon_name")
  private String couponName = null;

  @JsonProperty("percent")
  private Integer percent = null;

  public OrderListCoupon id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the coupon in a standard UUID format
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier of the coupon in a standard UUID format")

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public OrderListCoupon couponName(String couponName) {
    this.couponName = couponName;
    return this;
  }

  /**
   * Name of the coupon
   * @return couponName
  **/
  @ApiModelProperty(value = "Name of the coupon")


  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public OrderListCoupon percent(Integer percent) {
    this.percent = percent;
    return this;
  }

  /**
   * Discount percentage of the coupon
   * @return percent
  **/
  @ApiModelProperty(value = "Discount percentage of the coupon")


  public Integer getPercent() {
    return percent;
  }

  public void setPercent(Integer percent) {
    this.percent = percent;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderListCoupon orderListCoupon = (OrderListCoupon) o;
    return Objects.equals(this.id, orderListCoupon.id) &&
        Objects.equals(this.couponName, orderListCoupon.couponName) &&
        Objects.equals(this.percent, orderListCoupon.percent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, couponName, percent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderListCoupon {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    couponName: ").append(toIndentedString(couponName)).append("\n");
    sb.append("    percent: ").append(toIndentedString(percent)).append("\n");
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

