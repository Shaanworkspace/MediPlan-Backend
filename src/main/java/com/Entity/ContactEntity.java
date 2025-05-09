package com.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contacts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String phone;

    private String address;

    private String linkedinUrl;

    private String githubUrl;

    private String hackerrankUrl;

}

