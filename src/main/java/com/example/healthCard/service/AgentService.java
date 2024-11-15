package com.example.healthCard.service;

import com.example.healthCard.constants.ApplicationConstants;
import com.example.healthCard.dto.AgentInfoDto;
import com.example.healthCard.dto.ChiefInfoDto;
import com.example.healthCard.dto.FamilyMemberDto;
import com.example.healthCard.healthCardException.HealthCardException;
import com.example.healthCard.model.AgentEntity;
import com.example.healthCard.model.ChiefEntity;
import com.example.healthCard.model.HospitalEntity;
import com.example.healthCard.model.MemberEntity;
import com.example.healthCard.repo.AgentRepo;
import com.example.healthCard.repo.ChiefRepo;
import com.example.healthCard.repo.HospitalRepo;
import com.example.healthCard.repo.MemberRepo;
import com.example.healthCard.util.CommonUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AgentService {
  @Autowired AgentRepo agentRepo;

  @Autowired ChiefRepo chiefRepo;

  @Autowired MemberRepo memberRepo;

  @Autowired EmailService emailService;

  @Autowired HospitalRepo hospitalRepo;

  public void addAgent(AgentInfoDto agentInfoDto) {
    int code = CommonUtil.generateRandomNumber();
    AgentEntity agentEntity = new AgentEntity();
    agentEntity.setName(agentInfoDto.getName());
    agentEntity.setEmailAddress(agentInfoDto.getEmail());
    agentEntity.setPhoneNumber(agentInfoDto.getPhoneNumber());
    agentEntity.setIdentityType(agentInfoDto.getIdentityType());
    agentEntity.setAddress(agentInfoDto.getAddress());
    agentEntity.setIdentityNumber(agentInfoDto.getIdentityNumber());
    agentEntity.setActivationStatus(false);
    agentEntity.setSuspended(false);
    agentEntity.setCode(code);
    agentEntity.setRole(ApplicationConstants.USER_ROLES.AGENT.name());
    agentRepo.save(agentEntity);
    emailService.sendTempEmail(
        agentInfoDto.getName(), agentInfoDto.getEmail(), "Activation email", code);
  }

  public Page<AgentEntity> listAgents(int page, int size, String filter) {
    Pageable pageable = PageRequest.of(page, size);
    if (filter == null) {
      return agentRepo.findAll(pageable);
    }
    return agentRepo.findAllByEmailAddressContainsOrNameContains(filter, filter, pageable);
  }

  public void deleteAgent(String id) {
    agentRepo.deleteById(id);
  }

  public void agentSuspension(String id, boolean status) {
    AgentEntity agentEntity = agentRepo.findById(id).get();
    agentEntity.setSuspended(status);
    agentRepo.save(agentEntity);
  }

  /*    public ResponseEntity<Object> getAgentProfile(String id){
  }
  */

  public String addMembers(ChiefInfoDto chiefInfoDto) {

    try {
      Optional<AgentEntity> agentEntity = agentRepo.findById(chiefInfoDto.getAgentId());
      if (agentEntity.isEmpty()) {
        throw new HealthCardException("Invalid agent id.", 400);
      }

      Optional<HospitalEntity> optionalHospitalEntity =
          hospitalRepo.findByName(chiefInfoDto.getHospitalName());
      if (optionalHospitalEntity.isEmpty()) {
        throw new HealthCardException("Invalid hospital name.", 400);
      }
      HospitalEntity hospitalEntity = optionalHospitalEntity.get();
      AgentEntity agent = agentEntity.get();
      ChiefEntity chiefEntity = new ChiefEntity();
      int randomNumber = CommonUtil.generateRandomNumber();
      String cardNumber = "HC-" + randomNumber;
      chiefEntity.setId(cardNumber);
      chiefEntity.setName(chiefInfoDto.getName());
      chiefEntity.setEmailAddress(chiefInfoDto.getEmail());
      chiefEntity.setHospitalEntity(hospitalEntity);
      chiefEntity.setIdentityType(chiefInfoDto.getIdentityType());
      chiefEntity.setIdentityNumber(chiefInfoDto.getIdentityNumber());
      chiefEntity.setAddress(chiefInfoDto.getAddress());
      chiefEntity.setPhoneNumber(chiefInfoDto.getPhoneNumber());
      chiefEntity.setAgentEntity(agent);
      chiefEntity.setCreatedAt(LocalDateTime.now());
      chiefRepo.save(chiefEntity);
      List<FamilyMemberDto> familyMemberDtoList = chiefInfoDto.getFamilyMemberDtoList();
      for (FamilyMemberDto familyMemberDto : familyMemberDtoList) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(familyMemberDto.getName());
        memberEntity.setIdentityType(familyMemberDto.getIdentityType());
        memberEntity.setIdentityNumber(familyMemberDto.getIdentityNumber());
        memberEntity.setPhoneNumber(familyMemberDto.getPhoneNumber());
        memberEntity.setChiefEntity(chiefEntity);
        memberRepo.save(memberEntity);
      }
      return cardNumber;
    } catch (Exception exception) {
      throw new HealthCardException(exception.getLocalizedMessage(), 500);
    }
  }

  public void setAgentPassword(String email, int code, String password) {
    Optional<AgentEntity> optionalAgentEntity = agentRepo.findByEmailAddress(email);
    if (optionalAgentEntity.isEmpty()) {
      throw new HealthCardException("Invalid email address.", 500);
    }
    AgentEntity agentEntity = optionalAgentEntity.get();
    if (agentEntity.getCode() != code) {
      throw new HealthCardException("Invalid code.", 500);
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    agentEntity.setPassword(encoder.encode(password));
    agentEntity.setActivationStatus(true);
    agentRepo.save(agentEntity);
  }

  public void activateAgent(String email, int code) {
    Optional<AgentEntity> optionalAgentEntity = agentRepo.findByEmailAddress(email);
    if (optionalAgentEntity.isEmpty()) {
      throw new HealthCardException("Invalid email address.", 400);
    }
    AgentEntity agentEntity = optionalAgentEntity.get();
    if (agentEntity.getCode() != code) {
      throw new HealthCardException("Invalid code.", 400);
    }
  }
}
