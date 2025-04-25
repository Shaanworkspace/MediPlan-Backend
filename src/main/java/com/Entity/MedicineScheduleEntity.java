package com.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data

public class MedicineScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String medicineName;
    private String dosage;
    private LocalTime time;
    private String frequency;
    private LocalDate startDate;
    private LocalDate endDate;


    private boolean reminderEnabled;

    @ManyToOne
    private UserEntity user;
}
