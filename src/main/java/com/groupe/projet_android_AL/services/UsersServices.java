package com.groupe.projet_android_AL.services;

import com.groupe.projet_android_AL.auth.records.TokenPair;
import com.groupe.projet_android_AL.auth.services.AuthService;
import com.groupe.projet_android_AL.dtos.UsersLoginRequestDTO;
import com.groupe.projet_android_AL.dtos.UsersLoginResponseDTO;
import com.groupe.projet_android_AL.dtos.UsersRegisterRequestDTO;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.repositories.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersServices {
    private final UsersRepository usersRepository;
    private final AuthService authService;

    public UsersServices(UsersRepository repo, AuthService authService) {
        this.usersRepository = repo;
        this.authService = authService;
    }

    public UsersLoginResponseDTO registerUser(UsersRegisterRequestDTO usersDTO) {
        if (usersRepository.findByEmail(usersDTO.email).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        Users newUser = Users.builder()
                .firstName(usersDTO.firstName)
                .lastName(usersDTO.lastName)
                .email(usersDTO.email)
                .password(usersDTO.password)
                .build();

        newUser = usersRepository.save(newUser);
        TokenPair tokens = authService.createToken(newUser);
        return new UsersLoginResponseDTO(newUser, tokens);
    }

    public Users loginUser(UsersLoginRequestDTO usersDTO) {
        final Users user = usersRepository.findByEmail(usersDTO.email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!user.getPassword().equals(usersDTO.password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return user;
    }
}
