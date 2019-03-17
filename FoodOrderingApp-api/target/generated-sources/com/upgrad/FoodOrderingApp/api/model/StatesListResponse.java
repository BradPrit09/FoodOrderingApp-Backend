package com.upgrad.FoodOrderingApp.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.StatesList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * StatesListResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-18T01:17:55.741+05:30")

public class StatesListResponse   {
  @JsonProperty("states")
  @Valid
  private List<StatesList> states = null;

  public StatesListResponse states(List<StatesList> states) {
    this.states = states;
    return this;
  }

  public StatesListResponse addStatesItem(StatesList statesItem) {
    if (this.states == null) {
      this.states = new ArrayList<>();
    }
    this.states.add(statesItem);
    return this;
  }

  /**
   * List of states
   * @return states
  **/
  @ApiModelProperty(value = "List of states")

  @Valid

  public List<StatesList> getStates() {
    return states;
  }

  public void setStates(List<StatesList> states) {
    this.states = states;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatesListResponse statesListResponse = (StatesListResponse) o;
    return Objects.equals(this.states, statesListResponse.states);
  }

  @Override
  public int hashCode() {
    return Objects.hash(states);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatesListResponse {\n");
    
    sb.append("    states: ").append(toIndentedString(states)).append("\n");
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

