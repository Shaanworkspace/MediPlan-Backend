package com.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctors")
@Data                   // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Generates a no-arg constructor
@AllArgsConstructor     // Generates an all-arg constructor
@Builder
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;

    @Column(nullable = false)
    private String specialization;

    private Integer experience; // in years

    private String hospitalName;

    private boolean available;  // availability status
}
