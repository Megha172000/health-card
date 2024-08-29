package com.example.healthCard.controller;

import com.example.healthCard.dto.AdminDto;
import com.example.healthCard.dto.AgentInfoDto;
import com.example.healthCard.dto.AuthInfoDto;
import com.example.healthCard.dto.ChiefInfoDto;
import com.example.healthCard.handler.ResponseHandler;
import com.example.healthCard.healthCardException.HealthCardException;
import com.example.healthCard.model.AgentEntity;
import com.example.healthCard.model.ChiefEntity;
import com.example.healthCard.model.MemberEntity;
import com.example.healthCard.repo.AdminRepo;
import com.example.healthCard.repo.AgentRepo;
import com.example.healthCard.repo.ChiefRepo;
import com.example.healthCard.repo.MemberRepo;
import com.example.healthCard.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController

public class AgentController {

    @Autowired
    AgentRepo agentRepo;

    @Autowired
    AgentService agentService;

    @Autowired
    ChiefRepo chiefRepo;

    @Autowired
    MemberRepo memberRepo;

    @Autowired
    EmailController emailController;

    @Autowired
    AdminRepo adminRepo;

    @PostMapping("/admin-login")
    public ResponseEntity<ResponseHandler> agentLogin(@RequestBody AdminDto adminDto){
        if(adminRepo.existsByUserNameAndPassword(adminDto.getUserName(),adminDto.getPassword())){
            return ResponseHandler.getSuccessResponse("login");
        }
        else{
            return ResponseHandler.getErrorResponse(HttpStatus.NOT_FOUND,"Please enter valid email and password");
        }
    }

    @PostMapping("/add-agent")
    public ResponseEntity<Object> addAgent(@RequestBody AgentInfoDto agentInfoDto) {
        String emailAddress = agentInfoDto.getEmail();
        if (agentRepo.existsByEmailAddress(emailAddress)) {
            throw new HealthCardException("user already exist",450);
        }
        agentService.addAgent(agentInfoDto);
        return new ResponseEntity<>(agentInfoDto, HttpStatus.OK);

    }

    @GetMapping("/list-agent")
    public ResponseEntity<ResponseHandler> listOfAgent(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size,
                                          @RequestParam(required = false) String filter){
        Page<AgentEntity> agentEntitiesPage = agentService.listAgents(page, size, filter);
        List<AgentEntity> agentEntities = agentEntitiesPage.getContent();
        Pageable pageable = agentEntitiesPage.getPageable();
        ResponseHandler responseHandler = ResponseHandler.builder()
                .body(agentEntities.stream().map(this::convertToAgentInfoDto).toList())
                .code(1000)
                .pageable(pageable)
                .totalPages(agentEntitiesPage.getTotalPages())
                .build();
        return ResponseHandler.getSuccessResponse(responseHandler);
    }

    private AgentInfoDto convertToAgentInfoDto(AgentEntity agentEntity){
        return AgentInfoDto.builder()
                .id(agentEntity.getId())
                .phoneNumber(agentEntity.getPhoneNumber())
                .name(agentEntity.getName())
                .identityType(agentEntity.getIdentityType())
                .address(agentEntity.getAddress())
                .identityNumber(agentEntity.getIdentityNumber())
                .email(agentEntity.getEmailAddress())
                .address(agentEntity.getAddress())
                .build();
    }

    @DeleteMapping("/remove-agent")
    public ResponseEntity<ResponseHandler> deleteAgent(@RequestParam String id){
        if(agentRepo.findById(id).isEmpty()){
            return ResponseHandler.getErrorResponse(HttpStatus.NOT_FOUND, "Agent with id not found.");
        }
        agentService.deleteAgent(id);
        return ResponseHandler.getSuccessResponse("Agent deleted successfully.");
    }

    @PutMapping("/agent-suspension")
    public ResponseEntity<Object> deactivateAgent (@RequestParam String id,@RequestParam boolean status){
        if(agentRepo.findById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        agentService.agentSuspension(id,status);
        return new ResponseEntity<>("agent updated successfully" , HttpStatus.OK);
    }

    @GetMapping("get-agent-profile")
    public ResponseEntity<Object> getProfile(@RequestParam String id){
        Optional<AgentEntity> agentEntity  = agentRepo.findById(id);
        return new ResponseEntity<>(agentEntity,HttpStatus.OK);
    }

    @PutMapping("/update-agent-profile")
    public ResponseEntity<Object> updateProfile(@RequestBody AgentInfoDto agentInfoDto) {
        String id = agentInfoDto.getId();
        Optional<AgentEntity> agentEntity = agentRepo.findById(id);

        if (agentEntity.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            AgentEntity existingAgentEntity = agentEntity.get();
            existingAgentEntity.setName(agentInfoDto.getName());
            existingAgentEntity.setEmailAddress(agentInfoDto.getEmail());
            existingAgentEntity.setIdentityType(agentInfoDto.getIdentityType());
            existingAgentEntity.setAddress(agentInfoDto.getAddress());
            existingAgentEntity.setIdentityNumber(agentInfoDto.getIdentityNumber());
            agentRepo.save(existingAgentEntity);
        return new ResponseEntity<>(agentInfoDto, HttpStatus.OK);
    }

    @PostMapping("/add-members")
    public ResponseEntity<Object> addMembers(@RequestBody ChiefInfoDto chiefInfoDto){
        String emailAddress = chiefInfoDto.getEmail();
        if (chiefRepo.existsByEmailAddress(emailAddress)) {
            throw new HealthCardException("user already exist",450);
        }
        agentService.addMembers(chiefInfoDto);
        return new ResponseEntity<>(chiefInfoDto, HttpStatus.OK);
    }

    @PostMapping("/get-agent-member")
    public List<ChiefEntity> getAgentMember(@RequestParam String agentId, LocalDateTime localDateTime){
        List<ChiefEntity>  chiefEntityList =chiefRepo.findByAgentEntity_IdAndCreatedAt(agentId,localDateTime);
        return chiefEntityList;
    }

    @GetMapping("/list-chief")
    public ResponseEntity<Object> listOfMembers(){
        List<ChiefEntity> chiefEntityList = chiefRepo.findAll();
        return new ResponseEntity<>(chiefEntityList,HttpStatus.OK);
    }

    @GetMapping("/list-chief-members")
    public ResponseEntity<Object> listOfChefMembers(){
        List<MemberEntity> memberEntityList = memberRepo.findAll();
        return new ResponseEntity<>(memberEntityList,HttpStatus.OK);
    }

    @DeleteMapping("/delete-chief")
    public ResponseEntity<Object> deleteChief(@RequestParam String id){
        if(chiefRepo.findById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        chiefRepo.deleteById(id);
        return new ResponseEntity<>("chief deleted successfully",HttpStatus.OK);
    }

    @DeleteMapping("/delete-chief-members")
    public ResponseEntity<Object> deleteChiefMembers(@RequestParam String id){
        if(memberRepo.findById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        memberRepo.deleteById(id);
        return new ResponseEntity<>("member deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/number-of-agentandmemebers")
    public Map<String, Long> numOfAgentAndMembers(){
        long agentCount = 0;
        long chiefCount = 0;
        Map<String,Long> count = new HashMap<>();
        count.put("AgentCount",agentRepo.count());
        count.put("chiefCount",chiefRepo.count());
        return count;

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestParam String emailAddress){
        Optional<AgentEntity> agentEntity = agentRepo.findByEmailAddress(emailAddress);
        if(agentEntity.isEmpty()){
            return new ResponseEntity<>("email Address is not present",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("password Rest Sussessfully",HttpStatus.OK);
    }

    @PostMapping("/update-Password")
    public ResponseEntity<Object> updatePassword(@RequestBody AuthInfoDto authInfoDto){
        Optional<AgentEntity> agentEntity = agentRepo.findByEmailAddress(authInfoDto.getUsername());
        if(agentEntity.isEmpty()){
            return new ResponseEntity<>("email Address is not present",HttpStatus.NOT_FOUND);
        }
        AgentEntity agentEntity1 = agentEntity.get();
        agentEntity1.setPassword(authInfoDto.getPassword());
        agentRepo.save(agentEntity1);
        return new ResponseEntity<>("Password updated sussessfully",HttpStatus.OK);
    }

    @GetMapping("/agent-stats")
    public List<Map<String, String>> getAgentStats(@RequestParam String id){

        LocalDateTime endDate = LocalDateTime.now(); // Current date and time
        LocalDateTime startDate = endDate.minusDays(5); // Subtract days from the current date
        List<ChiefEntity> chiefEntityList = chiefRepo.findAllByAgentEntityIdAndCreatedAtBetween(id, startDate, endDate);
        List<Map<String, String>> outList = new ArrayList<>();
        chiefEntityList.forEach(chiefEntity -> {
            Map<String, String> output = new HashMap<>();
            output.put("name", chiefEntity.getAgentEntity().getName());
            output.put("date", chiefEntity.getCreatedAt().toString());
            output.put("cardNumber", chiefEntity.getId());
            output.put("contactNumber", String.valueOf(chiefEntity.getPhoneNumber()));
            outList.add(output);
        });

        return outList;
    }

}
