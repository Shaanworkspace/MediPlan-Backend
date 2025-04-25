package com.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class MedicineStockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicineName;
    private int quantityAvailable;
    private int lowStockThreshold;
    private boolean autoBuy;

    @ManyToOne
    private UserEntity user;

    private LocalDate lastUpdated;
}
