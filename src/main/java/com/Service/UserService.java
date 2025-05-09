package com.Service;

import com.DTO.Request.LoginRequest;
import com.Entity.UserEntity;
import com.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Boolean verifyEmailAndPassword(LoginRequest loginRequest){
        try {
            Optional<UserEntity> userByEmail = userRepository.findByEmail(loginRequest.getEmail());

            if (userByEmail.isEmpty()) {
                return false; // Email not found
            }

            UserEntity user = userByEmail.get();

            // TODO: Use BCryptPasswordEncoder in real-world apps instead of plain-text
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                return false; // Password does not match
            }

            return true; // Email and password match

        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            return false;
        }
    }

}
