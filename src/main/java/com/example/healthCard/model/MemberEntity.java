package com.example.healthCard.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "health_card_member")
public class MemberEntity {
  @jakarta.persistence.Id
  @Column(name = "Id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private String Id;

  @ManyToOne
  @JoinColumn(name = "chief_member_id", nullable = false)
  private ChiefEntity chiefEntity;

  @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<HealthCardConsultant> consultations;

  @Column(name = "name")
  private String name;

  @Column(name = "identity_type")
  private String identityType;

  @Column(name = "identity_number")
  private String identityNumber;

  @Column(name = "phone_number")
  private long phoneNumber;
}
