package com.example.healthCard.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "health_card_hospital")
public class HospitalEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private String id;

  @OneToMany(mappedBy = "hospitalEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<HealthCardConsultant> healthCardConsultantList;

  @Column(name = "name")
  private String name;

  @Column(name = "phone_number")
  private long phoneNumber;

  @Column(name = "address")
  private String address;

  @Column(name = "type")
  private String type;
}
