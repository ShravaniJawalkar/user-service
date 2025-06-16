package org.example.userservice.service;


import org.example.userservice.dao.UserLoginRequest;
import org.example.userservice.dao.UserRegistrationRequest;
import org.example.userservice.dao.UserResponse;
import org.example.userservice.dao.Users;
import org.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseEntity<UserResponse> registerUser(UserRegistrationRequest request) {
        // Validate the request
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        userRepository.save(user);
        if (user.getId() == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserResponse response = new UserResponse();
        response.setUserId(user.getId());
        response.setUserName(user.getUsername());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> getUserById(Long id) {
        Users user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserResponse response = new UserResponse();
        response.setUserId(user.getId());
        response.setUserName(user.getUsername());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<UserResponse> loginUser(UserLoginRequest request) {
        Users user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        UserResponse response = null;
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        response = new UserResponse();
        response.setUserId(user.getId());
        response.setUserName(user.getUsername());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<List<UserResponse>> getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Users> users = userRepository.findAll(pageRequest).getContent();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users.stream().map(user -> {
            UserResponse response = new UserResponse();
            response.setUserId(user.getId());
            response.setUserName(user.getUsername());
            return response;
        }).toList(), HttpStatus.OK);
    }
}
