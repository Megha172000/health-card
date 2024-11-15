package com.example.healthCard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChiefInfoDto {
  @JsonProperty(value = "agentId")
  private String agentId;

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "email")
  private String email;

  @JsonProperty(value = "hospitalName")
  private String hospitalName;

  @JsonProperty(value = "identityType")
  private String identityType;

  @JsonProperty(value = "identityNumber")
  private String identityNumber;

  @JsonProperty(value = "address")
  private String address;

  @JsonProperty(value = "phoneNumber")
  private long phoneNumber;

  @JsonProperty(value = "members")
  private List<FamilyMemberDto> familyMemberDtoList;
}
