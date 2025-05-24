package com.DTO.Request;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegisterRequest {
    private String firstName;
    private String lastName;
    @Email(message = "Invalid email format")
    private String email;
    private String password;
    private String confirmPassword;
    private String passwordWithoutEncryption;
    private String phone;
    private String adminRole;
}
