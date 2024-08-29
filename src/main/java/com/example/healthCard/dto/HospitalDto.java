package com.example.healthCard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HospitalDto {

  @JsonProperty(value = "name")
  private String Name;

  @JsonProperty(value = "phoneNumber")
  private long PhoneNumber;

  @JsonProperty(value = "address")
  private String Address;

  @JsonProperty(value = "type")
  private String Type;
}
