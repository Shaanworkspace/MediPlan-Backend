package com.Service;

import com.Entity.UserEntity;
import com.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity addUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    public List<UserEntity> addAllUsers(List<UserEntity> userEntities) {
        return userRepository.saveAll(userEntities);
    }

}
