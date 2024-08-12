package com.example.healthCard.controller;

import com.example.healthCard.dto.ConsultationDto;
import com.example.healthCard.handler.ResponseHandler;
import com.example.healthCard.healthCardException.HealthCardException;
import com.example.healthCard.model.HealthCardConsultant;
import com.example.healthCard.repo.HealthCardConsultantRepo;
import com.example.healthCard.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class HealthCardConsultantController {

    @Autowired
    HealthCardConsultantRepo healthCardConsultantRepo;

    @Autowired
    ConsultationService consultationService;

    @GetMapping("list-of-consultant")
    public ResponseEntity<ResponseHandler> listOfConsultant(@RequestParam String id){
        List<HealthCardConsultant> healthCardConsultantList = healthCardConsultantRepo.findAll();
        return ResponseHandler.getSuccessResponse(healthCardConsultantList);
    }

    @PostMapping("/add-consultation")
    public ResponseEntity<ResponseHandler> addConsultation(@RequestBody ConsultationDto consultationDto){
        try {
            consultationService.addConsultation(consultationDto);
            return ResponseHandler.getSuccessResponse("Consultation added successfully.");
        }catch (HealthCardException exception){
            return ResponseHandler.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getErrorMessage());
        }
    }


}
