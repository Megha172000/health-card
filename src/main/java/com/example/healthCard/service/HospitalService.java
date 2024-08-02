package com.example.healthCard.service;

import com.example.healthCard.dto.HospitalDto;
import com.example.healthCard.model.HospitalEntity;
import com.example.healthCard.repo.HospitalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class HospitalService {

    @Autowired
    HospitalRepo hospitalRepo;

    public void addHospital(HospitalDto hospitalDto){
        HospitalEntity hospitalEntity = new HospitalEntity();
        hospitalEntity.setName(hospitalDto.getName());
        hospitalEntity.setPhoneNumber(hospitalDto.getPhoneNumber());
        hospitalEntity.setAddress(hospitalDto.getAddress());
        hospitalEntity.setType(hospitalDto.getType());
        hospitalRepo.save(hospitalEntity);
    }

    public List<HospitalEntity> listHospital(){
        return hospitalRepo.findAll();
    }

    public void removeHospital(String id){
        Optional<HospitalEntity> hospitalEntity = hospitalRepo.findById(id);
        if(hospitalEntity.isEmpty()){
            /*return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);*/
        }
        if(id.equals(hospitalEntity.get().getId())){
            hospitalRepo.deleteById(id);
        }

    }
}
