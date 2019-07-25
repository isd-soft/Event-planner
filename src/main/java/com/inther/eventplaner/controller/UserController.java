package com.inther.eventplaner.controller;

import com.inther.eventplaner.exception.ResourceNotFoundException;
import com.inther.eventplaner.model.UserDAO;
import com.inther.eventplaner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public Optional<UserDAO> getUser(@PathVariable Integer userId) {
        return userRepository.findById(userId);
    }

    @PutMapping("/user/{userId}")
    public UserDAO updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDAO userRequest) {
        return userRepository.findById(userId).map(user -> {
            user.setFirstname(userRequest.getFirstname());
            user.setLastname(userRequest.getLastname());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setDescription(userRequest.getDescription());
            user.setGender(userRequest.getGender());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("UserID " + userId + " not found"));
    }
}



