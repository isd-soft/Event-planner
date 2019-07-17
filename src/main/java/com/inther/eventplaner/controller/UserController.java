package com.inther.eventplaner.controller;
import com.inther.eventplaner.domain.User;
import com.inther.eventplaner.model.UserDAO;
import com.inther.eventplaner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository,
                              BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /*@PostMapping("/signup")
    public String signUp(@RequestBody UserDAO user) {
        if (userRepository.findByEmail(user.getEmail())!=null){
            return "User with this email is already registered!";
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Congratulations! You successfully registered!";
    }*/

    /*@PostMapping(path = "/signin")
    public String authenticationMeth(@RequestBody User u) {
        // This returns a JSON or XML with the users
        if (userRepository.findById(u.getId()) != null &&
                userRepository.findByUsername(u.getUsername()).getUsername().equals(u.getUsername())
                && userRepository.findByUsername(u.getUsername()).getPassword().equals(u.getPassword())) {
            return "is valid. User name is:" + userRepository.findByUsername(u.getUsername()).getUsername() + " .Password is:"
                    + userRepository.findByUsername(u.getUsername()).getPassword();
        } else {
            return "no such user name";
        }
    }*/

    @GetMapping(path="/all")
    public Iterable<UserDAO> findAll(Map<String, Object> model) {
        // This returns a JSON or XML with the users
        Iterable<UserDAO> users=userRepository.findAll();
        model.put("utilizatori", users);
        return users;
    }
}



