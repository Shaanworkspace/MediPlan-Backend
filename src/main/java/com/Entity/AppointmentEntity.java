package com.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;

    // Step 2: Appointment Details
    private LocalDateTime appointmentDateTime;

    @Builder.Default
    private String doctorSpecialization = "Cardiology";

    @Builder.Default
    private String doctorName = "Not Assigned";

    @Builder.Default
    private String appointmentMode = "Offline";

    @Column(length = 500)
    @Builder.Default
    private String reasonForAppointment = "No reason specified";

    // Step 3: Medical History
    @Column(length = 1000)
    @Builder.Default
    private String previousMedicalConditions = "None";

    @Column(length = 1000)
    @Builder.Default
    private String medications = "None";

    @Column(length = 1000)
    @Builder.Default
    private String allergies = "None";

    // Step 4: Insurance
    @Builder.Default
    private String insuranceProvider = "Not Provided";

    @Builder.Default
    private String policyNumber = "Not Provided";

    @Builder.Default
    private String insuranceDocumentUrl = ""; // Optional file

    // Step 5: Reports/Uploads
    @Builder.Default
    private String medicalReportUrl = "";

    @Builder.Default
    private String prescriptionUrl = "";

    // Step 6: Payment
    @Builder.Default
    private String paymentMode = "Cash";

    @Builder.Default
    private String paymentReceiptUrl = "";

    // Step 7: Confirmation
    @Builder.Default
    private Boolean patientConsent = false;

    @Column(length = 1000)
    @Builder.Default
    private String additionalNotes = "";

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        // Double-check defaults if data comes through constructor and not builder
        if (this.doctorSpecialization == null) this.doctorSpecialization = "Cardiology";
        if (this.doctorName == null) this.doctorName = "Not Assigned";
        if (this.paymentMode == null) this.paymentMode = "Cash";
        if (this.patientConsent == null) this.patientConsent = false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
