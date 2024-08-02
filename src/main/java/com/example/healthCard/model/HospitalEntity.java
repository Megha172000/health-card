package com.example.healthCard.model;

import jakarta.persistence.*;
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

    @Column(name = "name")
    private String Name;

    @Column(name="phone_number")
    private long PhoneNumber;

    @Column(name = "address")
    private String Address;

    @Column(name = "type")
    private String Type;
}

