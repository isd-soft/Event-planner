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

    @PostMapping("/login")

    public String greeting(@RequestParam String username
            , @RequestParam String password, Model model) {

        return password;
    }


    @PostMapping(path = "/add")
    public  String addNewUser(@RequestParam String username
            , @RequestParam String password, Model model) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();
        n.setUsername(username);
        n.setPassword(password);
        userRepository.save(n);
        return "Saved user is " + n.getUsername() + " " + n.getPassword();
    }

    @GetMapping(path = "/all")
    public Iterable<User> findAll(Map<String, Object> model) {
        // This returns a JSON or XML with the users
        Iterable<User> user = userRepository.findAll();


        model.put("utilizatori", user);
        return user;
    }

    @PostMapping(path = "/authenticate")
    public String authenticationMeth(@RequestParam String username, @RequestParam String password) {
        // This returns a JSON or XML with the users

        if (userRepository.findByUsername(username).getUsername().equals(username)
               && userRepository.findByUsername(username).getPassword().equals(password) ) {

return "is valid. User name is:"+userRepository.findByUsername(username).getUsername()+" .Password is:"
        +userRepository.findByUsername(username).getPassword();

        }else{
            return "no such user name";

        }


    }


}



