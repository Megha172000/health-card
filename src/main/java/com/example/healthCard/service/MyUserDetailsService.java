package com.example.healthCard.service;

import com.example.healthCard.model.AdminEntity;
import com.example.healthCard.model.AgentEntity;
import com.example.healthCard.model.UserPrincipal;
import com.example.healthCard.repo.AdminRepo;
import com.example.healthCard.repo.AgentRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired private AdminRepo adminRepo;

  @Autowired private AgentRepo agentRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AdminEntity adminEntity = adminRepo.findByUserName(username);
    if (adminEntity == null) {
      Optional<AgentEntity> agentEntity = agentRepo.findByEmailAddress(username);
      if (agentEntity.isEmpty()) {
        throw new UsernameNotFoundException("user not found");
      } else {
        AdminEntity admin = new AdminEntity();
        admin.setUserName(agentEntity.get().getEmailAddress());
        admin.setPassword(agentEntity.get().getPassword());
        return new UserPrincipal(admin);
      }
    }
    return new UserPrincipal(adminEntity);
  }
}
