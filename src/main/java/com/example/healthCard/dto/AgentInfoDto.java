package com.example.healthCard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class AgentInfoDto {
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

    @JsonProperty(value = "id")
    private String id;
}
