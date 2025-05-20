package com.controller;


import com.Entity.MedicineScheduleEntity;
import com.Service.MedicineScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicine-schedule")
public class MedicineScheduleController {

    private final MedicineScheduleService medicineScheduleService;

    // Create or Update Medicine Schedule
    @PostMapping("/save")
    public ResponseEntity<MedicineScheduleEntity> saveMedicineSchedule(@RequestBody MedicineScheduleEntity medicineSchedule) {
        MedicineScheduleEntity savedSchedule = medicineScheduleService.saveOrUpdateMedicineSchedule(medicineSchedule);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSchedule);
    }

    // Get all Medicine Schedules
    @GetMapping("/all")
    public ResponseEntity<List<MedicineScheduleEntity>> getAllMedicineSchedules() {
        List<MedicineScheduleEntity> scheduleList = medicineScheduleService.getAllMedicineSchedules();
        return ResponseEntity.ok(scheduleList);
    }

    // Get Medicine Schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<MedicineScheduleEntity> getMedicineScheduleById(@PathVariable Long id) {
        Optional<MedicineScheduleEntity> schedule = medicineScheduleService.getMedicineScheduleById(id);
        return schedule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete Medicine Schedule
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedicineSchedule(@PathVariable Long id) {
        medicineScheduleService.deleteMedicineSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
