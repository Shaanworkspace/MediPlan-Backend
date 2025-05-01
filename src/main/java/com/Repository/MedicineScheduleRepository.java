package com.Repository;

import com.Entity.MedicineScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineScheduleRepository extends JpaRepository<MedicineScheduleEntity,Long> {
}
