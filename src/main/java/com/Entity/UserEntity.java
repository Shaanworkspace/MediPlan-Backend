package com.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role; // PATIENT or CAREGIVER
    private String phone;
    private String gender;
    private LocalDate dob;

    @OneToMany(mappedBy = "user")
    private List<MedicineScheduleEntity> schedules;

    @OneToMany(mappedBy = "user")
    private List<MedicineStock> stocks;
}
