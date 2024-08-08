package com.example.healthCard.service;

import com.example.healthCard.dto.AgentInfoDto;
import com.example.healthCard.dto.ChiefInfoDto;
import com.example.healthCard.dto.FamilyMemberDto;
import com.example.healthCard.healthCardException.HealthCardException;
import com.example.healthCard.model.AgentEntity;
import com.example.healthCard.model.ChiefEntity;
import com.example.healthCard.model.MemberEntity;
import com.example.healthCard.repo.AgentRepo;
import com.example.healthCard.repo.ChiefRepo;
import com.example.healthCard.repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentService {
    @Autowired
    AgentRepo agentRepo;

    @Autowired
    ChiefRepo chiefRepo;

    @Autowired
    MemberRepo memberRepo;

    @Autowired
    EmailService emailService;

    public void addAgent(AgentInfoDto agentInfoDto){
        AgentEntity agentEntity = new AgentEntity();
        agentEntity.setName(agentInfoDto.getName());
        agentEntity.setEmailAddress(agentInfoDto.getEmail());
        agentEntity.setIdentityType(agentInfoDto.getIdentityType());
        agentEntity.setAddress(agentInfoDto.getAddress());
        agentEntity.setIdentityNumber(agentInfoDto.getIdentityNumber());
        agentEntity.setActivationStatus(false);
        agentEntity.setSuspended(false);
        agentRepo.save(agentEntity);
        emailService.sendTempEmail(agentInfoDto.getName(),agentInfoDto.getEmail(),"Activation email");
    }

    public List<AgentEntity> ListOfAgent(){
        return agentRepo.findAll();
    }

    public void deleteAgent(String id){
        agentRepo.deleteById(id);
    }

    public void agentSuspension(String id,boolean status){
        AgentEntity agentEntity = agentRepo.findById(id).get();
        agentEntity.setSuspended(status);
        agentRepo.save(agentEntity);
    }

/*    public ResponseEntity<Object> getAgentProfile(String id){
    }
    */

    public void addMembers(ChiefInfoDto chiefInfoDto){
        Optional<AgentEntity> agentEntity = agentRepo.findById(chiefInfoDto.getAgentId());
        if(agentEntity.isEmpty()){
            throw new HealthCardException("invalid agent id",123);
        }
        AgentEntity agent  = agentEntity.get();
        ChiefEntity chiefEntity = new ChiefEntity();
        chiefEntity.setName(chiefInfoDto.getName());
        chiefEntity.setEmailAddress(chiefInfoDto.getEmail());
        chiefEntity.setIdentityType(chiefInfoDto.getIdentityType());
        chiefEntity.setIdentityNumber(chiefInfoDto.getIdentityNumber());
        chiefEntity.setAddress(chiefInfoDto.getAddress());
        chiefEntity.setPhoneNumber(chiefInfoDto.getPhoneNumber());
        chiefEntity.setAgentEntity(agent);
        chiefRepo.save(chiefEntity);
        List<FamilyMemberDto> familyMemberDtoList = chiefInfoDto.getFamilyMemberDtoList();
        for(FamilyMemberDto familyMemberDto :familyMemberDtoList){
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setName(familyMemberDto.getName());
            memberEntity.setIdentityType(familyMemberDto.getIdentityType());
            memberEntity.setIdentityNumber(familyMemberDto.getIdentityNumber());
            memberEntity.setPhoneNumber(familyMemberDto.getPhoneNumber());
            memberEntity.setChiefEntity(chiefEntity);
            memberRepo.save(memberEntity);
        }
    }


}

