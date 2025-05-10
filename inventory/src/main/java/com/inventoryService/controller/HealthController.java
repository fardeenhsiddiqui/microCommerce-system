package com.inventoryService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("e-com")
    public String helloWorld(){
        return "Fardeen is Here";
    }
}
