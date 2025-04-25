package com.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ReminderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime remainderDateTime;
    private String message;
    private boolean isSent;

    @ManyToOne
    private MedicineScheduleEntity medicineScheduleEntity;


}
