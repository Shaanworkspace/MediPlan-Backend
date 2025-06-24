package com.controller;

import com.Entity.DoctorEntity;
import com.Repository.DoctorRepository;
import com.Service.DoctorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorController {

    private final DoctorService doctorService;

    private final DoctorRepository doctorRepository;


    @GetMapping("/all")
    public ResponseEntity<List<DoctorEntity>> getAllDoctors() {
        System.out.println("Doctor getCall triggered");
        List<DoctorEntity> doctors = doctorRepository.findAll();
        return ResponseEntity.ok(doctors);
    }
    
    @PostMapping
    //Will receive only one Entity at a time
    public ResponseEntity<DoctorEntity> addDoctor(@Valid @RequestBody DoctorEntity doctorEntity){
        try{
            DoctorEntity doctorEntity1 = doctorService.addDoctor(doctorEntity);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(doctorEntity1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    @PostMapping("/add-all")
    public ResponseEntity<List<DoctorEntity>> addDoctors(@Valid @RequestBody List<DoctorEntity> doctorEntities) {
        try {
            List<DoctorEntity> savedDoctors = doctorService.addAllDoctors(doctorEntities);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedDoctors);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    @PutMapping("/{id}")
    public DoctorEntity updateDoctor(@PathVariable Long id, @RequestBody DoctorEntity updatedDoctor) {
        return doctorService.updateDoctor(id, updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        try {
            doctorRepository.deleteById(id);
            return ResponseEntity.ok("Doctor with ID " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting doctor: " + e.getMessage());
        }
    }


}
