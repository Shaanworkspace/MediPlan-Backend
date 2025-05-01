package com.Service;

import com.Entity.DoctorEntity;
import com.Entity.UserEntity;
import com.Repository.DoctorRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    @NotNull
    private DoctorEntity doctorEntity;

    @NotNull
    private final DoctorRepository doctorRepository;

    public DoctorEntity addDoctor(DoctorEntity doctorEntity){
        return doctorRepository.save(doctorEntity);
    }

    public List<DoctorEntity> addAllDoctors(List<DoctorEntity> doctorEntities) {
        return doctorRepository.saveAll(doctorEntities);
    }
}
