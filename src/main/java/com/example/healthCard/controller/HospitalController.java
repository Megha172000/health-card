package com.example.healthCard.controller;

import com.example.healthCard.dto.HospitalDto;
import com.example.healthCard.handler.ResponseHandler;
import com.example.healthCard.model.HospitalEntity;
import com.example.healthCard.repo.HospitalRepo;
import com.example.healthCard.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class HospitalController {
    @Autowired
    HospitalRepo hospitalRepo;

    @Autowired
    HospitalService hospitalService;


    @PostMapping("/add-hospital")
    public ResponseEntity<Object> hospitalEntry(@RequestBody HospitalDto hospitalDto) {
        hospitalService.addHospital(hospitalDto);
        return new ResponseEntity<>(hospitalDto, HttpStatus.OK);
    }

    @GetMapping("/list-of-hospital")
    public ResponseEntity<Object> listOfHospital() {
        List<HospitalEntity> hospitalEntity =hospitalService.listHospital();
        return new ResponseEntity<>(hospitalEntity,HttpStatus.OK);
    }

    @GetMapping("/get-hospitals")
    public Page<HospitalEntity> getHospitals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String filter) {
        return hospitalService.getHospitals(page, size, filter);
    }

    @DeleteMapping("/remove-hospital")
    public ResponseEntity<ResponseHandler> deleteHospital(@RequestParam String id){
        hospitalService.removeHospital(id);
        return ResponseHandler.getSuccessResponse("Hospital removed successfully.");

    }
}

