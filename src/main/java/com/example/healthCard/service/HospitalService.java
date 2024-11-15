package com.example.healthCard.service;

import com.example.healthCard.dto.HospitalDto;
import com.example.healthCard.handler.ResponseHandler;
import com.example.healthCard.model.HospitalEntity;
import com.example.healthCard.repo.HospitalRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {

  @Autowired HospitalRepo hospitalRepo;

  public void addHospital(HospitalDto hospitalDto) {
    HospitalEntity hospitalEntity = new HospitalEntity();

    hospitalEntity.setName(hospitalDto.getName());
    hospitalEntity.setPhoneNumber(hospitalDto.getPhoneNumber());
    hospitalEntity.setAddress(hospitalDto.getAddress());
    hospitalEntity.setType(hospitalDto.getType());
    hospitalRepo.save(hospitalEntity);
  }

  public List<HospitalEntity> listHospital() {
    return hospitalRepo.findAll();
  }

  public Page<HospitalEntity> getHospitals(int page, int size, String filter) {
    Pageable pageable = PageRequest.of(page, size);
    if (filter == null) {
      return hospitalRepo.findAll(pageable);
    }
    return hospitalRepo.findAllByNameContains(filter, pageable);
  }

  public void removeHospital(String id) {
    Optional<HospitalEntity> hospitalEntity = hospitalRepo.findById(id);
    if (hospitalEntity.isEmpty()) {
      /*return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);*/
    }
    if (id.equals(hospitalEntity.get().getId())) {
      hospitalRepo.deleteById(id);
    }
  }

  public ResponseEntity<ResponseHandler> listHospitalName() {
    List<String> hospitalNames = hospitalRepo.findHospitalNames();
    ResponseHandler responseHandler = ResponseHandler.builder().body(hospitalNames).build();
    return ResponseHandler.getSuccessResponse(responseHandler);
  }
}
