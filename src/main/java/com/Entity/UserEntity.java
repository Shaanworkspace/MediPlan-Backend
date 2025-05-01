package com.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Email @NotNull
    private String email;

    @NotNull
    private String password;

    private String phone;
    private String company;
    private LocalDate dob;

    @CreationTimestamp
    @Column(name = "time", updatable = false)
    private LocalDateTime localDateTimeOfRegistration;

    @OneToMany(mappedBy = "user")
    private List<MedicineScheduleEntity> schedules;

    @OneToMany(mappedBy = "user")
    private List<MedicineStockEntity> stocks;


}
