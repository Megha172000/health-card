package com.example.healthCard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class ChiefInfoDto {
    @JsonProperty(value = "agentId")
    private String agentId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "identityType")
    private String identityType;

    @JsonProperty(value = "identityNumber")
    private String identityNumber;

    @JsonProperty(value = "address")
    private String Address;

    @JsonProperty(value = "phoneNumber")
    private long phoneNumber;

    @JsonProperty(value = "members")
    private List<FamilyMemberDto> familyMemberDtoList;


}
