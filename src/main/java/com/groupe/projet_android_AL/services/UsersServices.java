package com.groupe.projet_android_AL.services;

import com.groupe.projet_android_AL.dtos.UsersRegisterRequestDTO;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.repositories.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersServices {
    private final UsersRepository usersRepository;

    public UsersServices(UsersRepository repo) {
        this.usersRepository = repo;
    }

    public Users registerUser(UsersRegisterRequestDTO usersDTO) {
        if (usersRepository.findByEmail(usersDTO.email).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        final Users newUser = Users.builder()
                .firstName(usersDTO.firstName)
                .lastName(usersDTO.lastName)
                .email(usersDTO.email)
                .password(usersDTO.password)
                .build();
        return usersRepository.save(newUser);
    }
}
