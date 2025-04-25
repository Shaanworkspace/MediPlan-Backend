package com.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CaregiverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity patient;

    @ManyToOne
    private UserEntity caregiver;

    private boolean allowAutoPurchase;
}
