package com.userService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class HealthController {

    @GetMapping("/e-com")
    public String helloWorld(){
        return "User Service";
    }
}
