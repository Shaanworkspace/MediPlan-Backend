package com.Service;

import com.DTO.Request.LoginRequest;
import com.Entity.UserEntity;
import com.Repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserEntityRepository userEntityRepository;

    public List<UserEntity> getAllUsers() {
        return userEntityRepository.findAll();
    }

    public UserEntity addUser(UserEntity userEntity){
        return userEntityRepository.save(userEntity);
    }

    public List<UserEntity> addAllUsers(List<UserEntity> userEntities) {
        return userEntityRepository.saveAll(userEntities);
    }

    public Boolean verifyEmailAndPassword(LoginRequest loginRequest){
        try {
            Optional<UserEntity> userByEmail = userEntityRepository.findByEmail(loginRequest.getEmail());

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Email not found" + username));
    }
}
