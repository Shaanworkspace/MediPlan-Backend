package com.controller;


import com.Entity.MedicineStockEntity;
import com.Service.MedicineStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicine-stock")
public class MedicineStockController {

    private final MedicineStockService medicineStockService;

    // Create or Update Medicine Stock
    @PostMapping("/save")
    public ResponseEntity<MedicineStockEntity> saveOrUpdateMedicineStock(@RequestBody MedicineStockEntity medicineStock) {
        MedicineStockEntity savedStock = medicineStockService.saveOrUpdateMedicineStock(medicineStock);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStock);
    }

    // Get all Medicine Stocks
    @GetMapping("/all")
    public ResponseEntity<List<MedicineStockEntity>> getAllMedicineStocks() {
        List<MedicineStockEntity> stockList = medicineStockService.getAllMedicineStocks();
        return ResponseEntity.ok(stockList);
    }

    // Get Medicine Stock by ID
    @GetMapping("/{id}")
    public ResponseEntity<MedicineStockEntity> getMedicineStockById(@PathVariable Long id) {
        Optional<MedicineStockEntity> stock = medicineStockService.getMedicineStockById(id);
        return stock.isPresent() ? ResponseEntity.ok(stock.get()) : ResponseEntity.notFound().build();
        //we can write this as
        //return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    // Delete Medicine Stock
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedicineStock(@PathVariable Long id) {
        medicineStockService.deleteMedicineStock(id);
        return ResponseEntity.noContent().build();
    }
}

