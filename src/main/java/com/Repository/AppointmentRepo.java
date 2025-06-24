package com.Repository;

import com.Entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<AppointmentEntity,Long> {
    List<AppointmentEntity> findByUserId(Long userId);

}
