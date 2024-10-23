package com.example.healthCard.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "health_card_agent")
public class AgentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private String id;

  @OneToMany(mappedBy = "agentEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ChiefEntity> chiefEntityList;

  @Column(name = "email_address")
  private String emailAddress;

  @Column(name = "phone_number")
  private Long phoneNumber;

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

  @Column(name = "password")
  private String password;

  @Column(name = "code")
  private Integer code;

  @Column(name = "role")
  private String role;
}
