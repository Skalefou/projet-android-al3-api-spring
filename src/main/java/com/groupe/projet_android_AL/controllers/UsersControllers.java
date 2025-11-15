package com.groupe.projet_android_AL.controllers;

import com.groupe.projet_android_AL.auth.annotations.CurrentUser;
import com.groupe.projet_android_AL.dtos.users.UsersLoginRequestDTO;
import com.groupe.projet_android_AL.dtos.users.UsersLoginResponseDTO;
import com.groupe.projet_android_AL.dtos.users.UsersRegisterRequestDTO;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.services.UsersServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UsersControllers {
    private final UsersServices usersServices;

    public UsersControllers(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    @PostMapping("/register")
    public UsersLoginResponseDTO registerUser(@Valid @RequestBody UsersRegisterRequestDTO body) {
        return usersServices.registerUser(body);
    }

    @PostMapping("/login")
    public Users loginUser(@RequestBody UsersLoginRequestDTO body) {
        return usersServices.loginUser(body);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint(@CurrentUser Users user) {
        return ResponseEntity.ok("Test endpoint is working! current user email: " + user.getEmail());
    }
}
