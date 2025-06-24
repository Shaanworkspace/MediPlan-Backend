package com.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String specialty;

    @Column
    private String phoneNumber;

    @Column
    private String address;

    @Column(name = "available", nullable = false, columnDefinition = "BIT DEFAULT 1")
    private boolean available = true;
}
