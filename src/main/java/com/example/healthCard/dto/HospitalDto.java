package com.example.healthCard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HospitalDto {

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "phoneNumber")
  private long phoneNumber;

  @JsonProperty(value = "address")
  private String address;

  @JsonProperty(value = "type")
  private String type;
}
