package com.example.healthCard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "agent_login")

public class AdminEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

}
