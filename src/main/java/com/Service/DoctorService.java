package com.Service;

import com.Entity.DoctorEntity;
import com.Entity.UserEntity;
import com.Repository.DoctorRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorEntity addDoctor(DoctorEntity doctorEntity){
        return doctorRepository.save(doctorEntity);
    }

    public List<DoctorEntity> addAllDoctors(List<DoctorEntity> doctorEntities) {
        return doctorRepository.saveAll(doctorEntities);
    }

    public Optional<DoctorEntity> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public List<DoctorEntity> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<DoctorEntity> getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    public DoctorEntity updateDoctor(Long id, DoctorEntity doctorDetails) {
        DoctorEntity doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        doctor.setFirstName(doctorDetails.getFirstName());
        doctor.setLastName(doctorDetails.getLastName());
        doctor.setEmail(doctorDetails.getEmail());
        doctor.setSpecialty(doctorDetails.getSpecialty());
        doctor.setPhoneNumber(doctorDetails.getPhoneNumber());
        doctor.setAddress(doctorDetails.getAddress());
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        DoctorEntity doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctorRepository.delete(doctor);
    }
}
