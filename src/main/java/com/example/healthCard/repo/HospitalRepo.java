package com.example.healthCard.repo;

import com.example.healthCard.model.HospitalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface HospitalRepo extends JpaRepository<HospitalEntity,String> {

    Page<HospitalEntity> findAllByNameContains(String name, Pageable pageable);
}
