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

    @GetMapping("/userinfo/{userId}")
    public Optional<UserDAO> getUserInfo(@PathVariable Integer userId) {
        return userRepository.findById(userId);
    }

    @PutMapping("/userinfo/{userId}")
    public UserDAO updateUserInfo(@PathVariable Integer userId, @Valid @RequestBody UserDAO userInfoRequest) {
        return userRepository.findById(userId).map(userInfo -> {
            userInfo.setFirstname(userInfoRequest.getFirstname());
            userInfo.setLastname(userInfoRequest.getLastname());
            userInfo.setPhoneNumber(userInfoRequest.getPhoneNumber());
            userInfo.setDescription(userInfoRequest.getDescription());
            userInfo.setGender(userInfoRequest.getGender());
            return userRepository.save(userInfo);
        }).orElseThrow(() -> new ResourceNotFoundException("UserID " + userId + " not found"));
    }
}



