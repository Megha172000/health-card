package com.example.healthCard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)


public class AdminDto {
    @JsonProperty(value = "userName")
    private String userName;

    @JsonProperty(value = "password")
    private String password;

}
