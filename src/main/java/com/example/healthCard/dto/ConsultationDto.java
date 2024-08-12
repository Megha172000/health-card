package com.example.healthCard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDto {

    private String hospitalId;
    private String customerId;
    private String healthCardId;
}
