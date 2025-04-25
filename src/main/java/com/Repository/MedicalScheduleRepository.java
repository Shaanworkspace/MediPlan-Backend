package com.Repository;

import com.Entity.MedicineScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalScheduleRepository extends JpaRepository<MedicineScheduleEntity,Long> {
}
