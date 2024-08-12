package com.example.healthCard.repo;

import com.example.healthCard.model.HealthCardConsultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface HealthCardConsultantRepo extends JpaRepository<HealthCardConsultant,String> {

}
