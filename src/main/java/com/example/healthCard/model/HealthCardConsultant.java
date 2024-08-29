package com.example.healthCard.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "health_card_consultant")
public class HealthCardConsultant {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private String id;

  @ManyToOne
  @JoinColumn(name = "hospital_id", nullable = false)
  private HospitalEntity hospitalEntity;

  @ManyToOne
  @JoinColumn(name = "chief_member_id", nullable = true)
  private ChiefEntity chiefEntity;

  @ManyToOne
  @JoinColumn(name = "member_id", nullable = true)
  private MemberEntity memberEntity;

  @Column(name = "healthcard_id")
  private String healthCardId;

  @Column(name = "visited_at")
  private Long visitedTimeStamp;
}
