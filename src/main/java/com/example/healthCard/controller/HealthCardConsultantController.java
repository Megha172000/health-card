package com.example.healthCard.controller;

import com.example.healthCard.handler.ResponseHandler;
import com.example.healthCard.model.HealthCardConsultant;
import com.example.healthCard.repo.HealthCardConsultantRepo;
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

    @GetMapping("list-of-consultant")
    public ResponseEntity<ResponseHandler> listOfConsultant(@RequestParam String id){
        List<HealthCardConsultant> healthCardConsultantList = healthCardConsultantRepo.findAll();
        return ResponseHandler.getSuccessResponse(healthCardConsultantList);
    }
/*    @GetMapping("get_Consultant_By_Id")
    public ResponseEntity<ResponseHandler> ConsultantById(@RequestParam String id){

    }*/


}
