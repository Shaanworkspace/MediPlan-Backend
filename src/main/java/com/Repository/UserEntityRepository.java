package com.Repository;

import com.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);
}
