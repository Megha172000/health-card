package com.example.healthCard.repo;

import com.example.healthCard.model.AgentEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepo extends JpaRepository<AgentEntity, String> {
  Optional<AgentEntity> findByEmailAddress(String emailAddress);

  Boolean existsByEmailAddress(String emailAddress);

  Optional<AgentEntity> findByEmailAddressAndPassword(String emailAddress, String password);

  Page<AgentEntity> findAllByEmailAddressContainsOrNameContains(
      String emailAddress, String name, Pageable pageable);
}
