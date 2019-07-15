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
public class GreetingController {
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting" ;
    }

//    @GetMapping("/login")
//    public String getMap() {
////        model.addAttribute("name", name);
//        return "redirect:/login" ;
//    }
//
    @GetMapping("/login")
//    @PostMapping("/login")
//@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String greeting(@RequestParam String username
            , @RequestParam String password, Model model) {
//        model.addAttribute("name", name);
        return "password: " +password;
    }


    @PostMapping(path="/add") // Map ONLY GET Requests
    public @ResponseBody String addNewUser (@RequestParam String username
            , @RequestParam String password, Model model) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();
        n.setUsername(username);
        n.setPassword(password);
        userRepository.save(n);
//        model.addAttribute("name", username);
//        return "content"+ username;
        return "Saved user is "+ n.getUsername()+" "+ n.getPassword();
    }

    @GetMapping(path="/all")
    public Iterable<User> findAll(Map<String, Object> model) {
        // This returns a JSON or XML with the users
        Iterable<User> user=userRepository.findAll();


        model.put("utilizatori",user );
        return user;
    }

    @PostMapping(path="/authenticate")
    public String authenticationMeth(@RequestParam String username,@RequestParam String password) {
        // This returns a JSON or XML with the users

      if( userRepository.findByUsername(username).getUsername().equals(username)
              &&userRepository.findByUsername(username).getPassword().equals(password)){





      };
        return "pass";
    }


}



