package com.Entity;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicineName;

    private String dosageAmount; // e.g., 500mg, 1 tablet
    private String dosageInstruction; // e.g., After meal

    @ElementCollection
    private List<LocalTime> doseTakeTime; // Take medicine at multiple times (08:00, 20:00)

    private String frequency; // e.g., "Daily", "Alternate Days", "Custom"

    @ElementCollection
    private List<DayOfWeek> daysOfWeek; // For weekly patterns (e.g., MON, WED, FRI)

    private LocalDate startDate;
    private LocalDate endDate;

    private boolean reminderEnabled;

    private String notes; // Optional: patient-specific instructions

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "medicineSchedule", cascade = CascadeType.ALL)
    private List<DoseTimeEntity> doseTimes;
}
