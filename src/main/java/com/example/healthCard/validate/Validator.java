package com.example.healthCard.validate;

import com.example.healthCard.dto.AgentInfoDto;
import com.example.healthCard.dto.ConsultationDto;
import com.example.healthCard.dto.HospitalDto;
import com.example.healthCard.healthCardException.HealthCardException;
import org.springframework.http.HttpStatus;

public class Validator {

  public static void validateAgent(AgentInfoDto agentInfoDto) {
    if (agentInfoDto.getName() == null || agentInfoDto.getName().isEmpty()) {
      throw new HealthCardException("You must enter a name.", HttpStatus.BAD_REQUEST.value());
    }
    if (agentInfoDto.getEmail() == null || agentInfoDto.getEmail().isEmpty()) {
      throw new HealthCardException("You must enter a email.", HttpStatus.BAD_REQUEST.value());
    }
    if (agentInfoDto.getIdentityType() == null || agentInfoDto.getIdentityType().isEmpty()) {
      throw new HealthCardException(
          "You must enter a identityType.", HttpStatus.BAD_REQUEST.value());
    }
    if (agentInfoDto.getIdentityNumber() == null || agentInfoDto.getIdentityNumber().isEmpty()) {
      throw new HealthCardException(
          "You must enter a identityNumber.", HttpStatus.BAD_REQUEST.value());
    }
    if (agentInfoDto.getAddress() == null || agentInfoDto.getAddress().isEmpty()) {
      throw new HealthCardException("You must enter a address.", HttpStatus.BAD_REQUEST.value());
    }
    if (String.valueOf(agentInfoDto.getPhoneNumber()).length() != 10) {
      throw new HealthCardException(
          "you must enter a valid phoneNumber.", HttpStatus.BAD_REQUEST.value());
    }
  }

  public static void validateHospital(HospitalDto hospitalDto) {
    if (hospitalDto.getName() == null || hospitalDto.getName().isEmpty()) {
      throw new HealthCardException(
          "You must enter a hospitalName.", HttpStatus.BAD_REQUEST.value());
    }
    if (hospitalDto.getAddress() == null || hospitalDto.getAddress().isEmpty()) {
      throw new HealthCardException(
          "You must enter a hospitalAddress.", HttpStatus.BAD_REQUEST.value());
    }
    if (String.valueOf(hospitalDto.getPhoneNumber()).length() != 10) {
      throw new HealthCardException(
          "you must enter a valid phoneNumber.", HttpStatus.BAD_REQUEST.value());
    }

    if (hospitalDto.getType() == null || hospitalDto.getType().isEmpty()) {
      throw new HealthCardException("You must enter a type.", HttpStatus.BAD_REQUEST.value());
    }
  }

  public static void validateConsultation(ConsultationDto consultationDto) {
    if (consultationDto.getHospitalId() == null || consultationDto.getHospitalId().isEmpty()) {
      throw new HealthCardException("You must enter a hospitalId.", HttpStatus.BAD_REQUEST.value());
    }
    if (consultationDto.getCustomerId() == null || consultationDto.getCustomerId().isEmpty()) {
      throw new HealthCardException("You must enter a customerId.", HttpStatus.BAD_REQUEST.value());
    }
    if (consultationDto.getHealthCardId() == null || consultationDto.getHealthCardId().isEmpty()) {
      throw new HealthCardException(
          "You must enter a healthCardId.", HttpStatus.BAD_REQUEST.value());
    }
  }
}
