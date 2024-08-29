package com.example.healthCard.controller;

import com.example.healthCard.dto.AuthInfoDto;
import com.example.healthCard.handler.ResponseHandler;
import com.example.healthCard.healthCardException.HealthCardException;
import com.example.healthCard.model.AgentEntity;
import com.example.healthCard.repo.AgentRepo;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @Autowired AgentRepo agentRepo;

  @PostMapping("/login")
  public ResponseEntity<ResponseHandler> login(@RequestBody AuthInfoDto authInfoDto) {
    Map<String, String> response = new HashMap<>();
    try {
      Optional<AgentEntity> optionalAgentEntity =
          agentRepo.findByEmailAddressAndPassword(
              authInfoDto.getUsername(), authInfoDto.getPassword());
      if (optionalAgentEntity.isEmpty()) {
        throw new HealthCardException("Email address or password incorrect.", 1400);
      }
      response.put("jwt", "token");
      response.put("redirectUrl", "/dashboard"); // URL to redirect to on success
      return ResponseHandler.getSuccessResponse(response);
    } catch (HealthCardException exception) {
      response.put("error", "The email address or password you entered is incorrect.");
      return ResponseHandler.getErrorResponse(HttpStatus.CONFLICT, exception.getErrorMessage());
    } catch (Exception exception) {
      response.put("error", "Internal server error occurred.");
      return ResponseHandler.getErrorResponse(
          HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage());
    }
  }
}
