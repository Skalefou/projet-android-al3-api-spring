package com.groupe.projet_android_AL.controllers;

import com.groupe.projet_android_AL.dtos.UsersLoginRequestDTO;
import com.groupe.projet_android_AL.dtos.UsersLoginResponseDTO;
import com.groupe.projet_android_AL.dtos.UsersRegisterRequestDTO;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.services.UsersServices;
import jakarta.validation.Valid;
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
}
