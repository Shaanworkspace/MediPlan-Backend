package com.Service;
import com.Entity.MedicineStockEntity;
import com.Repository.MedicineStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicineStockService {
    private final MedicineStockRepository medicineStockRepository;

    // Create or Update Medicine Stock
    public MedicineStockEntity saveOrUpdateMedicineStock(MedicineStockEntity medicineStock) {
        return medicineStockRepository.save(medicineStock);
    }

    // Get all Medicine Stocks
    public List<MedicineStockEntity> getAllMedicineStocks() {
        return medicineStockRepository.findAll();
    }

    // Get Medicine Stock by ID
    public Optional<MedicineStockEntity> getMedicineStockById(Long id) {
        return medicineStockRepository.findById(id);
    }

    // Delete Medicine Stock
    public void deleteMedicineStock(Long id) {
        medicineStockRepository.deleteById(id);
    }
}
