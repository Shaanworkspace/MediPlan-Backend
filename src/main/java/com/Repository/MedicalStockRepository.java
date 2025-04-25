package com.Repository;

import com.Entity.MedicineStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalStockRepository extends JpaRepository<MedicineStockEntity,Long> {
}
