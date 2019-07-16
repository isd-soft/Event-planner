package com.inther.eventplaner.controller;

import com.inther.eventplaner.domain.User;
import com.inther.eventplaner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GreetingController {
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }


    //    @GetMapping("/login")
    @PostMapping("/login")

    public String greeting(@RequestParam String username
            , @RequestParam String password, Model model) {

        return password;
    }


    @PostMapping(path = "/signup")
    public   String addNewUser( @RequestBody User u) {

if (userRepository.findByUsername(u.getUsername()).getEmail()!=null){
    return "user exists";
}else {
        userRepository.save(u);
        return "Saved user is " + u.getUsername() + " " + u.getPassword();
}
    }

    @GetMapping(path = "/all")
    public Iterable<User> findAll(Map<String, Object> model) {
        // This returns a JSON or XML with the users
        Iterable<User> user = userRepository.findAll();


        model.put("utilizatori", user);
        return user;
    }

    @PostMapping(path = "/signin")
    public String authenticationMeth(@RequestBody User u) {
        // This returns a JSON or XML with the users

        if (userRepository.findByUsername(u.getUsername()).getUsername().equals(u.getUsername())
                && userRepository.findByUsername(u.getUsername()).getPassword().equals(u.getPassword())) {

            return "is valid. User name is:" + userRepository.findByUsername(u.getUsername()).getUsername() + " .Password is:"
                    + userRepository.findByUsername(u.getUsername()).getPassword();

        } else {
            return "no such user name";

        }


    }

}






