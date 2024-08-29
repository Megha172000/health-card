package com.example.healthCard.repo;

import com.example.healthCard.model.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<AdminEntity,String> {

    public Boolean existsByUserNameAndPassword (String userName,String password);
}
