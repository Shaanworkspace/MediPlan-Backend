package com.controller;

import com.DTO.Request.LoginRequest;
import com.DTO.Request.RegisterRequest;
import com.DTO.Response.AuthResponse;
import com.Entity.UserEntity;
import com.Entity.UserRoles;
import com.Repository.UserEntityRepository;
import com.enums.Role;
import com.Service.AuthenticationService;
import com.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityRepository userEntityRepository;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            userService.loadUserByUsername(registerRequest.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse(null, null, null, null, null, "User already exists"));
        } catch (UsernameNotFoundException e) {
            try {

                // Validate confirmPassword
                if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new AuthResponse(null, null, null, null, null, "Passwords do not match with confirm password"));
                }

                // Create new user
                UserEntity user = new UserEntity();
                user.setEmail(registerRequest.getEmail());
                user.setPasswordWithoutEncryption(registerRequest.getPassword());
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                user.setFirstName(registerRequest.getFirstName());
                user.setLastName(registerRequest.getLastName());
                user.setPhone(registerRequest.getPhone());
                user.setCompany(registerRequest.getCompany());
                user.setEnabled(true);
                user.setAccountNonExpired(true);
                user.setCredentialsNonExpired(true);
                user.setAccountNonLocked(true);

                // Assign role userRole ( need user and its role ) for user
                UserRoles userRole = new UserRoles();
                userRole.setUser(user);
                String role1 = registerRequest.getRole();
                if(role1.isEmpty()){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new AuthResponse(null, null, null, null, null, "Please fill the Role "));
                }
                try{
                    Role role = Role.valueOf(role1.toUpperCase());
                    userRole.setRole(role);
                }catch (IllegalArgumentException e1){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new AuthResponse(null, null, null, null, null, "Invalid role: " + role1));

                }

                // Saving a list of user Role in user
                user.setRoles(List.of(userRole));

                // Save user
                userService.addUser(user);

                // Generate token
                String token = authenticationService.verify(registerRequest.getEmail(), registerRequest.getPassword());
                return ResponseEntity.ok(new AuthResponse(token, user.getEmail(),user.getFirstName(),user.getLastName(), List.of(userRole.getRole().name()),"Done"));
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new AuthResponse(null, null, null, null, null, "Registration failed: " + ex.getMessage()));
            }
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String token = authenticationService.verify(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );

            UserDetails user = userService.loadUserByUsername(loginRequest.getEmail());
            //Now we need to fetch the roles of user so that we can divert to respective pages
            //we can typecast this in userEntity as it implements UserDetails
            UserEntity user1 = (UserEntity) user;
            List<String> roles = user1.getRoles().stream()
                    .map(userRole -> userRole.getRole().name())
                    .toList();

            return ResponseEntity.ok(
                    new AuthResponse(token, loginRequest.getEmail(),user1.getFirstName() , user1.getLastName(), roles,"Created , No error")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, null,  null, null,"Invalid email or password"));
        }
    }

}


