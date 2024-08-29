package com.example.healthCard.repo;

import com.example.healthCard.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<MemberEntity, String> {}
