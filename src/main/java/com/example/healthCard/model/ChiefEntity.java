package com.example.healthCard.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "health_card_chief")
public class ChiefEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private String id;

  @OneToMany(mappedBy = "chiefEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<MemberEntity> memberEntityList;

  @OneToMany(mappedBy = "chiefEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<HealthCardConsultant> consultations;

  // Use a different column name for the foreign key reference
  @ManyToOne
  @JoinColumn(name = "agent_id", nullable = false)
  private AgentEntity agentEntity;

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

  @Column(name = "phone_number")
  private long phoneNumber;

  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
