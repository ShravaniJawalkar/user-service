package org.example.userservice.controller;


import jakarta.validation.Valid;
import org.example.userservice.dao.UserLoginRequest;
import org.example.userservice.dao.UserRegistrationRequest;
import org.example.userservice.dao.UserResponse;
import org.example.userservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UsersService usersService;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegistrationRequest userRequest){
        System.out.println("Received user: " + userRequest.getUsername());
        return usersService.registerUser(userRequest);
    }
    @PostMapping("/sessions")
    public ResponseEntity<UserResponse> loginUser(@Valid @RequestBody UserLoginRequest userRequest){
        return  usersService.loginUser(userRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return usersService.getUserById(id);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return usersService.getAllUsers(page, size);
    }
}
