package com.example.healthCard.repo;

import com.example.healthCard.model.ChiefEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiefRepo extends JpaRepository<ChiefEntity, String> {
  Optional<ChiefEntity> findByEmailAddress(String emailAddress);

  boolean existsByEmailAddress(String emailAddress);

  List<ChiefEntity> findByAgentEntity_IdAndCreatedAt(String agentId, LocalDateTime createdAt);

  List<ChiefEntity> findAllByAgentEntityIdAndCreatedAtBetween(
      String agentId, LocalDateTime startDate, LocalDateTime endDate);

  Page<ChiefEntity> findAllByAgentEntityId(String agentId, Pageable pageable);

  Page<ChiefEntity> findAllByIdAndAgentEntityId(String id, String agentId, Pageable pageable);

  Page<ChiefEntity> findAllById(String id, Pageable pageable);
}
