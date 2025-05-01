package com.Controller;

import com.Entity.UserEntity;
import com.Repository.UserRepository;
import com.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


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

}
