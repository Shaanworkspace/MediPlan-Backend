package com.controller;

import com.Entity.UserEntity;
import com.Entity.UserRoles;
import com.enums.Role;
import com.Service.AuthenticationService;
import com.Service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            userService.loadUserByUsername(registerRequest.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse(null, null, null, "User already exists"));
        } catch (UsernameNotFoundException e) {
            try {

                // Validate confirmPassword
                if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new AuthResponse(null, null, null, "Passwords do not match with confirm password"));
                }

                // Create new user
                UserEntity user = new UserEntity();
                user.setEmail(registerRequest.getEmail());
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                user.setFirstName(registerRequest.getFirstName());
                user.setLastName(registerRequest.getLastName());
                user.setPhone(registerRequest.getPhone());
                user.setCompany(registerRequest.getCompany());
                user.setEnabled(true);
                user.setAccountNonExpired(true);
                user.setCredentialsNonExpired(true);
                user.setAccountNonLocked(true);

                // Assign role
                UserRoles userRole = new UserRoles();
                userRole.setUser(user);
                userRole.setRole(Role.PATIENT);
                user.setRoles(List.of(userRole));

                // Save user
                userService.addUser(user);

                // Generate token
                String token = authenticationService.verify(registerRequest.getEmail(), registerRequest.getPassword());
                return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), List.of(Role.PATIENT.name())));
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new AuthResponse(null, null, null, "Registration failed: " + ex.getMessage()));
            }
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody RegisterRequest loginRequest) {
        try {
            String token = authenticationService.verify(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );
            return ResponseEntity.ok(
                    new AuthResponse(token, loginRequest.getEmail(), List.of(Role.PATIENT.name()))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, null, "Invalid email or password"));
        }
    }

}

@Data
class AuthResponse {
    private String token;
    private String username;
    private List<String> roles;
    private String error;

    public AuthResponse(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    public AuthResponse(String token, String username, List<String> roles, String error) {
        this.token = token;
        this.username = username;
        this.roles = roles;
        this.error = error;
    }

}

@Data
class RegisterRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String phone;
    private String company;
}