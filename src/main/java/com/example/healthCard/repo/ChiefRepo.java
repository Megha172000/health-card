package com.example.healthCard.repo;

import com.example.healthCard.model.ChiefEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ChiefRepo extends JpaRepository<ChiefEntity,String>{
    Optional<ChiefEntity> findByEmailAddress(String emailAddress);

    boolean existsByEmailAddress(String emailAddress);

}
