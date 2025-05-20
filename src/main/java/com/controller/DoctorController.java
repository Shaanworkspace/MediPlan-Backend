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
public class DoctorController {
    @NotNull
    private final DoctorService doctorService;

    private final DoctorRepository doctorRepository;
    private  DoctorEntity doctorEntity;

    @GetMapping("/all")
    public ResponseEntity<List<DoctorEntity>> getAllDoctors() {
        List<DoctorEntity> doctors = doctorRepository.findAll();
        return ResponseEntity.ok(doctors);
    }
    
    @PostMapping("/add")
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


}
