package com.Repository;

import com.Entity.CaregiverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface caregiverRepository extends JpaRepository<CaregiverEntity,Long> {
}
