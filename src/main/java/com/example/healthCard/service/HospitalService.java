package com.example.healthCard.service;

import com.example.healthCard.PaginatedResponse;
import com.example.healthCard.constants.ApplicationConstants;
import com.example.healthCard.dto.HospitalDto;
import com.example.healthCard.handler.ResponseHandler;
import com.example.healthCard.healthCardException.HealthCardException;
import com.example.healthCard.model.AgentEntity;
import com.example.healthCard.model.HospitalEntity;
import com.example.healthCard.repo.HospitalRepo;
import com.example.healthCard.util.CommonUtil;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {

  @Autowired HospitalRepo hospitalRepo;
  @Autowired EmailService emailService;

  public void addHospital(HospitalDto hospitalDto) {
    if (hospitalRepo.existsByEmailAddress(hospitalDto.getEmailAddress())) {
      throw new HealthCardException(
          "Hospital with email address already exist.", HttpStatus.BAD_REQUEST.value());
    }
    HospitalEntity hospitalEntity = new HospitalEntity();

    int code = CommonUtil.generateRandomNumber();

    hospitalEntity.setName(hospitalDto.getName());
    hospitalEntity.setPhoneNumber(hospitalDto.getPhoneNumber());
    hospitalEntity.setEmailAddress(hospitalDto.getEmailAddress());
    hospitalEntity.setAddress(hospitalDto.getAddress());
    hospitalEntity.setType(hospitalDto.getType());
    hospitalEntity.setCode(code);
    hospitalRepo.save(hospitalEntity);

    emailService.sendTempEmail(
        hospitalDto.getName(),
        hospitalDto.getEmailAddress(),
        "Activation email",
        code,
        ApplicationConstants.OPERATION.ACTIVATE_HOSPITAL.name());
  }

  public List<HospitalEntity> listHospital() {
    return hospitalRepo.findAll();
  }

  public PaginatedResponse getHospitals(int page, int size, String filter) {
    Pageable pageable = PageRequest.of(page, size);
    Page<HospitalEntity> hospitalEntityPage;
    if (filter == null) {
      hospitalEntityPage = hospitalRepo.findAll(pageable);
    } else {
      hospitalEntityPage = hospitalRepo.findAllByNameContains(filter, pageable);
    }
    List<HospitalEntity> hospitalEntities = hospitalEntityPage.getContent();
    List<HospitalDto> hospitalDtos =
        hospitalEntities.stream().map(this::convertToHospitalDto).toList();
    return PaginatedResponse.builder()
        .first(hospitalEntityPage.isFirst())
        .last(hospitalEntityPage.isLast())
        .content(hospitalDtos)
        .numberOfElements(hospitalEntityPage.getNumberOfElements())
        .size(hospitalEntityPage.getSize())
        .totalElements(hospitalEntityPage.getNumberOfElements())
        .totalPages(hospitalEntityPage.getTotalPages())
        .pageable(hospitalEntityPage.getPageable())
        .build();
  }

  private HospitalDto convertToHospitalDto(HospitalEntity hospitalEntity) {
    return HospitalDto.builder()
        .id(hospitalEntity.getId())
        .address(hospitalEntity.getAddress())
        .type(hospitalEntity.getType())
        .emailAddress(hospitalEntity.getEmailAddress())
        .name(hospitalEntity.getName())
        .phoneNumber(hospitalEntity.getPhoneNumber())
        .build();
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

  public void activateHospital(String email, int code) {
    Optional<HospitalEntity> optionalHospitalEntity = hospitalRepo.findByEmailAddress(email);
    if (optionalHospitalEntity.isEmpty()) {
      throw new HealthCardException("Invalid email address.", 400);
    }
    HospitalEntity hospitalEntity = optionalHospitalEntity.get();
    if (hospitalEntity.getCode() != code) {
      throw new HealthCardException("Invalid code.", 400);
    }
  }

  public void setHospitalPassword(String email, int code, String password) {
    Optional<HospitalEntity> optionalHospitalEntity = hospitalRepo.findByEmailAddress(email);
    if (optionalHospitalEntity.isEmpty()) {
      throw new HealthCardException("Invalid email address.", 500);
    }
    HospitalEntity hospitalEntity = optionalHospitalEntity.get();
    if (hospitalEntity.getCode() != code) {
      throw new HealthCardException("Invalid code.", 500);
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    hospitalEntity.setPassword(encoder.encode(password));
    hospitalRepo.save(hospitalEntity);
  }
}
