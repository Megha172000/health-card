package com.example.healthCard.controller;

import com.example.healthCard.PaginatedResponse;
import com.example.healthCard.dto.HospitalDto;
import com.example.healthCard.handler.ResponseHandler;
import com.example.healthCard.healthCardException.HealthCardException;
import com.example.healthCard.model.HospitalEntity;
import com.example.healthCard.repo.HospitalRepo;
import com.example.healthCard.service.HospitalService;
import com.example.healthCard.validate.Validator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HospitalController {
  @Autowired HospitalRepo hospitalRepo;

  @Autowired HospitalService hospitalService;

  @PostMapping("/add-hospital")
  public ResponseEntity<ResponseHandler> hospitalEntry(@RequestBody HospitalDto hospitalDto) {
    try {
      Validator.validateHospital(hospitalDto);
      hospitalService.addHospital(hospitalDto);
      return ResponseHandler.getSuccessResponse("Hospital details added successfully.");
    } catch (HealthCardException healthCardException) {
      return ResponseHandler.getErrorResponse(
          HttpStatus.valueOf(healthCardException.getErrorCode()),
          healthCardException.getErrorMessage());
    }
  }

  @GetMapping("/list-of-hospital")
  public ResponseEntity<Object> listOfHospital() {
    List<HospitalEntity> hospitalEntity = hospitalService.listHospital();
    return new ResponseEntity<>(hospitalEntity, HttpStatus.OK);
  }

  @GetMapping("/get-hospitals")
  public PaginatedResponse getHospitals(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false) String filter) {
    return hospitalService.getHospitals(page, size, filter);
  }

  @DeleteMapping("/remove-hospital")
  public ResponseEntity<ResponseHandler> deleteHospital(@RequestParam String id) {
    hospitalService.removeHospital(id);
    return ResponseHandler.getSuccessResponse("Hospital removed successfully.");
  }

  @GetMapping("/list-hospital-name")
  public ResponseEntity<ResponseHandler> listHospitalName() {
    ResponseEntity<ResponseHandler> response = hospitalService.listHospitalName();
    return ResponseHandler.getSuccessResponse(response);
  }
}
