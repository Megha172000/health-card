package com.example.healthCard.service;

import com.example.healthCard.dto.ChiefDto;
import com.example.healthCard.dto.MemberDto;
import com.example.healthCard.handler.ResponseHandler;
import com.example.healthCard.model.ChiefEntity;
import com.example.healthCard.model.MemberEntity;
import com.example.healthCard.repo.ChiefRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  @Autowired ChiefRepo chiefRepo;

  public List<ChiefDto> listMembers(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<ChiefEntity> chiefEntityPage = chiefRepo.findAll(pageable);
    List<ChiefEntity> chiefEntityList = chiefEntityPage.getContent();
    return chiefEntityList.stream().map(this::convertToChiefDto).toList();
  }

  public ResponseEntity<ResponseHandler> listMembersByAgentId(
      int page, int size, String agentId, String filter) {
    Pageable pageable = PageRequest.of(page, size);
    Page<ChiefEntity> chiefEntityPage;
    if (filter != null && !filter.isEmpty()) {
      chiefEntityPage = chiefRepo.findAllByIdAndAgentEntityId(filter, agentId, pageable);
    } else {
      chiefEntityPage = chiefRepo.findAllByAgentEntityId(agentId, pageable);
    }
    List<ChiefEntity> chiefEntityList = chiefEntityPage.getContent();

    List<ChiefDto> chiefDtos = chiefEntityList.stream().map(this::convertToChiefDto).toList();

    ResponseHandler responseHandler =
        ResponseHandler.builder()
            .body(chiefDtos)
            .code(1000)
            .pageable(pageable)
            .totalPages(chiefEntityPage.getTotalPages())
            .build();
    return ResponseHandler.getSuccessResponse(responseHandler);
  }

  public ResponseEntity<ResponseHandler> listMembersByHospitalId(
      int page, int size, String hospitalId, String filter) {
    Pageable pageable = PageRequest.of(page, size);
    Page<ChiefEntity> chiefEntityPage;
    if (filter != null && !filter.isEmpty()) {
      chiefEntityPage = chiefRepo.findAllByIdOrHospitalEntityId(filter, hospitalId, pageable);
    } else {
      chiefEntityPage = chiefRepo.findAllByHospitalEntityId(hospitalId, pageable);
    }
    List<ChiefEntity> chiefEntityList = chiefEntityPage.getContent();

    List<ChiefDto> chiefDtos = chiefEntityList.stream().map(this::convertToChiefDto).toList();

    ResponseHandler responseHandler =
        ResponseHandler.builder()
            .body(chiefDtos)
            .code(1000)
            .pageable(pageable)
            .totalPages(chiefEntityPage.getTotalPages())
            .build();
    return ResponseHandler.getSuccessResponse(responseHandler);
  }

  public ResponseEntity<ResponseHandler> listAllMembers(int page, int size, String filter) {
    Pageable pageable = PageRequest.of(page, size);
    Page<ChiefEntity> chiefEntityPage;
    if (filter != null && !filter.isEmpty()) {
      chiefEntityPage = chiefRepo.findAllById(filter, pageable);
    } else {
      chiefEntityPage = chiefRepo.findAll(pageable);
    }
    List<ChiefEntity> chiefEntityList = chiefEntityPage.getContent();

    List<ChiefDto> chiefDtos = chiefEntityList.stream().map(this::convertToChiefDto).toList();

    ResponseHandler responseHandler =
        ResponseHandler.builder()
            .body(chiefDtos)
            .code(1000)
            .pageable(pageable)
            .totalPages(chiefEntityPage.getTotalPages())
            .build();
    return ResponseHandler.getSuccessResponse(responseHandler);
  }

  public ChiefDto convertToChiefDto(ChiefEntity chiefEntity) {
    List<MemberEntity> memberEntities = chiefEntity.getMemberEntityList();
    List<MemberDto> memberDtos = memberEntities.stream().map(this::convertToMemberDto).toList();
    return ChiefDto.builder()
        .id(chiefEntity.getId())
        .name(chiefEntity.getName())
        .emailAddress(chiefEntity.getEmailAddress())
        .identityType(chiefEntity.getIdentityType())
        .phoneNumber(chiefEntity.getPhoneNumber())
        .identityNumber(chiefEntity.getIdentityNumber())
        .address(chiefEntity.getAddress())
        .createdAt(chiefEntity.getCreatedAt())
        .members(memberDtos)
        .build();
  }

  public MemberDto convertToMemberDto(MemberEntity memberEntity) {
    return MemberDto.builder()
        .phoneNumber(memberEntity.getPhoneNumber())
        .id(memberEntity.getId())
        .name(memberEntity.getName())
        .build();
  }
}
