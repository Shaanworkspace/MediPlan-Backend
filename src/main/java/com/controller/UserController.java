package com.controller;

import com.DTO.Request.LoginRequest;
import com.Entity.UserEntity;
import com.Entity.UserRoles;
import com.Repository.UserEntityRepository;
import com.Service.UserService;
import com.enums.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;
    private final UserEntityRepository userEntityRepository;

    @GetMapping("/email/{email}")
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable String email) {
        UserEntity user = userEntityRepository.findByEmail(email);
        if(user==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add")
    public ResponseEntity<UserEntity> addUser(@Valid @RequestBody UserEntity userEntity){
        try{

            UserEntity userEntity1 = userService.addUser(userEntity);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userEntity1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    @PostMapping("/add-all")
    public ResponseEntity<List<UserEntity>> addUsers(@Valid @RequestBody List<UserEntity> userEntities) {
        try {
            List<UserEntity> savedUsers = userService.addAllUsers(userEntities);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedUsers);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }
    @PutMapping("/bulk-update-roles")
    public ResponseEntity<String> bulkUpdateRolesToPatientAndCaregiver() {
        try {
            // Fetch all users
            List<UserEntity> users = userEntityRepository.findAll();

            // Update roles for each user
            for (UserEntity user : users) {
                // Clear existing roles
                user.getRoles().clear();

                // Add PATIENT role
                UserRoles patientRole = new UserRoles();
                patientRole.setUser(user);
                patientRole.setRole(Role.PATIENT);
                user.getRoles().add(patientRole);

                // Add CAREGIVER role
                UserRoles caregiverRole = new UserRoles();
                caregiverRole.setUser(user);
                caregiverRole.setRole(Role.CARE_GIVER);
                user.getRoles().add(caregiverRole);
            }

            // Save all users in a single transaction
            userEntityRepository.saveAll(users);

            return ResponseEntity.ok("Roles updated successfully for all users to PATIENT and CAREGIVER");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating roles: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        try {
            // Fetch the existing user
            UserEntity existingUser = userEntityRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            // Update basic fields
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setCompany(updatedUser.getCompany());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setDob(updatedUser.getDob());

            // Update security flags
            existingUser.setEnabled(updatedUser.isEnabled());
            existingUser.setAccountNonExpired(updatedUser.isAccountNonExpired());
            existingUser.setCredentialsNonExpired(updatedUser.isCredentialsNonExpired());
            existingUser.setAccountNonLocked(updatedUser.isAccountNonLocked());

            // Update roles
            // Clear existing roles and add new ones
            existingUser.getRoles().clear();
            if (updatedUser.getRoles() != null) {
                List<UserRoles> newRoles = updatedUser.getRoles().stream()
                        .map(roleEntity -> {
                            UserRoles userRole = new UserRoles();
                            userRole.setUser(existingUser);
                            userRole.setRole(roleEntity.getRole() != null ? roleEntity.getRole() : Role.PATIENT); // Default to PATIENT if null
                            return userRole;
                        })
                        .toList();
                existingUser.getRoles().addAll(newRoles);
            }

            // Save the updated user
            UserEntity savedUser = userEntityRepository.save(existingUser);

            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/loginUser")
    public ResponseEntity<UserEntity> loginUser(@RequestBody LoginRequest loginRequest){
        Boolean isValid = userService.verifyEmailAndPassword(loginRequest);
        if (isValid) {
            UserEntity byEmail = userEntityRepository.findByEmail(loginRequest.getEmail());
            return ResponseEntity.ok(byEmail);
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        try {
            userEntityRepository.deleteById(id); // Assuming UserEntity represents patients
            return ResponseEntity.ok("Patient with ID " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting patient: " + e.getMessage());
        }
    }
}
