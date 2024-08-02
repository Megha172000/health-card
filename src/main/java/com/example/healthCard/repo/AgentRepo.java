package com.example.healthCard.repo;

import com.example.healthCard.dto.AgentInfoDto;
import com.example.healthCard.model.AgentEntity;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepo extends JpaRepository<AgentEntity,String> {
    Optional<AgentEntity> findByEmailAddress(String emailAddress);
    Boolean existsByEmailAddress(String emailAddress);


}
