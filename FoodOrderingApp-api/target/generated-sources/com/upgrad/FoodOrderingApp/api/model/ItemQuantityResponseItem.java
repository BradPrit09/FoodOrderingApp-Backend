package com.upgrad.FoodOrderingApp.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ItemQuantityResponseItem
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-18T10:17:18.974+05:30")

public class ItemQuantityResponseItem   {
  @JsonProperty("id")
  private UUID id = null;

  @JsonProperty("item_name")
  private String itemName = null;

  @JsonProperty("item_price")
  private Integer itemPrice = null;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    VEG("VEG"),
    
    NON_VEG("NON_VEG");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("type")
  private TypeEnum type = null;

  public ItemQuantityResponseItem id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the item in a standard UUID format
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier of the item in a standard UUID format")

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public ItemQuantityResponseItem itemName(String itemName) {
    this.itemName = itemName;
    return this;
  }

  /**
   * Name of the item
   * @return itemName
  **/
  @ApiModelProperty(value = "Name of the item")


  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public ItemQuantityResponseItem itemPrice(Integer itemPrice) {
    this.itemPrice = itemPrice;
    return this;
  }

  /**
   * Price of the item
   * @return itemPrice
  **/
  @ApiModelProperty(value = "Price of the item")


  public Integer getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(Integer itemPrice) {
    this.itemPrice = itemPrice;
  }

  public ItemQuantityResponseItem type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")


  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemQuantityResponseItem itemQuantityResponseItem = (ItemQuantityResponseItem) o;
    return Objects.equals(this.id, itemQuantityResponseItem.id) &&
        Objects.equals(this.itemName, itemQuantityResponseItem.itemName) &&
        Objects.equals(this.itemPrice, itemQuantityResponseItem.itemPrice) &&
        Objects.equals(this.type, itemQuantityResponseItem.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, itemName, itemPrice, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemQuantityResponseItem {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    itemPrice: ").append(toIndentedString(itemPrice)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

