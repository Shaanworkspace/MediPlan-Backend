package com.Repository;

import com.Entity.ReminderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<ReminderEntity,Long> {
}
