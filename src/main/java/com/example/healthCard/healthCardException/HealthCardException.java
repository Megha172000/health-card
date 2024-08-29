package com.example.healthCard.healthCardException;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthCardException extends RuntimeException {
  private String errorMessage;
  private int errorCode;
}
