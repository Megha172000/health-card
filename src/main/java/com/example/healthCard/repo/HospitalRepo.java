package com.example.healthCard.repo;

import com.example.healthCard.model.HospitalEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepo extends JpaRepository<HospitalEntity, String> {

  Page<HospitalEntity> findAllByNameContains(String name, Pageable pageable);

  // Custom query to fetch only the hospital names
  @Query("SELECT h.name FROM HospitalEntity h")
  List<String> findHospitalNames();

  Optional<HospitalEntity> findByName(String name);

  boolean existsByEmailAddress(String emailAddress);

  Optional<HospitalEntity> findByEmailAddress(String emailAddress);
}
