package com.upgrad.FoodOrderingApp.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.ItemQuantityResponseItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ItemQuantityResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-18T08:42:25.757+05:30")

public class ItemQuantityResponse   {
  @JsonProperty("item")
  private ItemQuantityResponseItem item = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  @JsonProperty("price")
  private Integer price = null;

  public ItemQuantityResponse item(ItemQuantityResponseItem item) {
    this.item = item;
    return this;
  }

  /**
   * Get item
   * @return item
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ItemQuantityResponseItem getItem() {
    return item;
  }

  public void setItem(ItemQuantityResponseItem item) {
    this.item = item;
  }

  public ItemQuantityResponse quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Quantity of item ordered
   * @return quantity
  **/
  @ApiModelProperty(value = "Quantity of item ordered")


  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public ItemQuantityResponse price(Integer price) {
    this.price = price;
    return this;
  }

  /**
   * Total price of the item
   * @return price
  **/
  @ApiModelProperty(value = "Total price of the item")


  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemQuantityResponse itemQuantityResponse = (ItemQuantityResponse) o;
    return Objects.equals(this.item, itemQuantityResponse.item) &&
        Objects.equals(this.quantity, itemQuantityResponse.quantity) &&
        Objects.equals(this.price, itemQuantityResponse.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, quantity, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemQuantityResponse {\n");
    
    sb.append("    item: ").append(toIndentedString(item)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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

