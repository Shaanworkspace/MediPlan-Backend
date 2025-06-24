package com.Service;

import com.DTO.Request.LoginRequest;
import com.Entity.UserEntity;
import com.Entity.UserRoles;
import com.Repository.UserEntityRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @NotNull
    private final UserEntityRepository userEntityRepository;

    public List<UserEntity> getAllUsers() {
        return userEntityRepository.findAll();
    }

    public UserEntity addUser(UserEntity userEntity){
        if (userEntity.getRoles() != null) {
            for (UserRoles role : userEntity.getRoles()) {
                role.setUser(userEntity); // 👈 VERY IMPORTANT!
            }
        }
        return userEntityRepository.save(userEntity);
    }

    public List<UserEntity> addAllUsers(List<UserEntity> userEntities) {
        return userEntityRepository.saveAll(userEntities);
    }

    public Boolean verifyEmailAndPassword(LoginRequest loginRequest){
        try {
            UserEntity userByEmail = userEntityRepository.findByEmail(loginRequest.getEmail());

            if (userByEmail==null) {
                return false; // Email not found
            }

            // TODO: Use BCryptPasswordEncoder in real-world apps instead of plain-text
            if (!userByEmail.getPassword().equals(loginRequest.getPassword())) {
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
        UserEntity byEmail = userEntityRepository.findByEmail(username);
        if(byEmail == null) {
            throw new UsernameNotFoundException("Can't Get Username");
        };
        return byEmail;
    }
}
