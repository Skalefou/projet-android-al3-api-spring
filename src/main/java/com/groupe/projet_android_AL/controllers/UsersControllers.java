package com.groupe.projet_android_AL.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UsersControllers {
    @PostMapping("/register")
    public String registerUser() {
        return "User registered successfully!";
    }
}
