package com.example.healthCard.repo;

import com.example.healthCard.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository

public interface MemberRepo extends JpaRepository<MemberEntity,String> {


}
