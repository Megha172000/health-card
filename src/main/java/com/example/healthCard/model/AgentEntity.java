package com.example.healthCard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "health_card_agent")
public class AgentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @OneToMany(mappedBy = "agentEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ChiefEntity> chiefEntityList;

    @Column(name = "email_Address")
    private String emailAddress;

    @Column(name = "name")
    private String name;

    @Column(name = "identity_type")
    private String identityType;

    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "activation_status")
    private boolean activationStatus;

    @Column(name = "suspended")
    private boolean suspended;
}
