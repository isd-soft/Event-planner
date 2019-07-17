package com.inther.eventplaner.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForAuthenticatedUsersController {

    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Page only for authenticated users!";
    }

}
