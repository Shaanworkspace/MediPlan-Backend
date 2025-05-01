package com.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime doseTime;

    @ManyToOne
    @JoinColumn(name = "medicine_schedule_id", nullable = false)
    private MedicineScheduleEntity medicineSchedule;
}