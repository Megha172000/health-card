package com.example.healthCard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class HospitalDto {

  @JsonProperty(value = "id")
  private String id;

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "phoneNumber")
  private long phoneNumber;

  @JsonProperty(value = "emailAddress")
  private String emailAddress;

  @JsonProperty(value = "address")
  private String address;

  @JsonProperty(value = "type")
  private String type;
}
