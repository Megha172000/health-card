package com.example.healthCard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyMemberDto {

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "identityType")
  private String identityType;

  @JsonProperty(value = "identityNumber")
  private String identityNumber;

  @JsonProperty(value = "phoneNumber")
  private long phoneNumber;
}
