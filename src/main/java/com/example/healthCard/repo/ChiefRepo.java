package com.example.healthCard.repo;

import com.example.healthCard.model.ChiefEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiefRepo extends JpaRepository<ChiefEntity, String> {
  Optional<ChiefEntity> findByEmailAddress(String emailAddress);

  boolean existsByEmailAddress(String emailAddress);

  List<ChiefEntity> findByAgentEntity_IdAndCreatedAt(String agentId, LocalDateTime createdAt);

  List<ChiefEntity> findAllByAgentEntityIdAndCreatedAtBetween(
      String agentId, LocalDateTime startDate, LocalDateTime endDate);
}
