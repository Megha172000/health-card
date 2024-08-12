package com.example.healthCard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "health_card_consultant")
public class HealthCardConsultant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospitalEntity;

    @Column(name = "healthcard_id")
    private String healthCardId;

    @Column(name = "visited_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date visitedAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
