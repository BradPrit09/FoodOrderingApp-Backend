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
 * RestaurantUpdatedResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-18T08:42:25.497+05:30")

public class RestaurantUpdatedResponse   {
  @JsonProperty("id")
  private UUID id = null;

  @JsonProperty("status")
  private String status = null;

  public RestaurantUpdatedResponse id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the restaurant in a standard UUID format
   * @return id
  **/
  @ApiModelProperty(required = true, value = "Unique identifier of the restaurant in a standard UUID format")
  @NotNull

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public RestaurantUpdatedResponse status(String status) {
    this.status = status;
    return this;
  }

  /**
   * status of the updated restaurant
   * @return status
  **/
  @ApiModelProperty(required = true, value = "status of the updated restaurant")
  @NotNull


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestaurantUpdatedResponse restaurantUpdatedResponse = (RestaurantUpdatedResponse) o;
    return Objects.equals(this.id, restaurantUpdatedResponse.id) &&
        Objects.equals(this.status, restaurantUpdatedResponse.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestaurantUpdatedResponse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

