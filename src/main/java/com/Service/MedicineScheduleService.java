package com.Service;

import com.Entity.MedicineScheduleEntity;
import com.Repository.MedicineScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicineScheduleService {
    private final MedicineScheduleRepository medicineScheduleRepository;

    // Create or Update Medicine Schedule
    public MedicineScheduleEntity saveOrUpdateMedicineSchedule(MedicineScheduleEntity medicineSchedule) {
        return medicineScheduleRepository.save(medicineSchedule);
    }

    // Get all Medicine Schedules
    public List<MedicineScheduleEntity> getAllMedicineSchedules() {
        return medicineScheduleRepository.findAll();
    }

    // Get Medicine Schedule by ID
    public Optional<MedicineScheduleEntity> getMedicineScheduleById(Long id) {
        return medicineScheduleRepository.findById(id);
    }

    // Delete Medicine Schedule
    public void deleteMedicineSchedule(Long id) {
        medicineScheduleRepository.deleteById(id);
    }
}
