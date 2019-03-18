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
 * ItemQuantity
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-18T08:42:25.757+05:30")

public class ItemQuantity   {
  @JsonProperty("item_id")
  private UUID itemId = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  @JsonProperty("price")
  private Integer price = null;

  public ItemQuantity itemId(UUID itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * Unique identifier of the item in a standard UUID format
   * @return itemId
  **/
  @ApiModelProperty(value = "Unique identifier of the item in a standard UUID format")

  @Valid

  public UUID getItemId() {
    return itemId;
  }

  public void setItemId(UUID itemId) {
    this.itemId = itemId;
  }

  public ItemQuantity quantity(Integer quantity) {
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

  public ItemQuantity price(Integer price) {
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
    ItemQuantity itemQuantity = (ItemQuantity) o;
    return Objects.equals(this.itemId, itemQuantity.itemId) &&
        Objects.equals(this.quantity, itemQuantity.quantity) &&
        Objects.equals(this.price, itemQuantity.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, quantity, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemQuantity {\n");
    
    sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
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

