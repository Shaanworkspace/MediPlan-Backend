package com.Repository;

import com.Entity.MedicineStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineStockRepository extends JpaRepository<MedicineStockEntity,Long> {
}
