package com.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Step 1: Personal Info
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String contactNumber;
    private String email;

    @Column(length = 500)
    private String address;

    // Step 2: Appointment Details
    private LocalDateTime appointmentDateTime;
    private String doctorSpecialization;
    private String doctorName;
    private String appointmentMode;

    @Column(length = 500)
    private String reasonForAppointment;

    // Step 3: Medical History
    @Column(length = 1000)
    private String previousMedicalConditions;

    @Column(length = 1000)
    private String medications;

    @Column(length = 1000)
    private String allergies;

    // Step 4: Insurance
    private String insuranceProvider;
    private String policyNumber;

    private String insuranceDocumentUrl; // File upload URL/path

    // Step 5: Reports/Uploads
    private String medicalReportUrl; // File upload URL/path
    private String prescriptionUrl;

    // Step 6: Payment
    private String paymentMode;
    private String paymentReceiptUrl;

    // Step 7: Confirmation
    private Boolean patientConsent;

    @Column(length = 1000)
    private String additionalNotes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
