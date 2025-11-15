package com.groupe.projet_android_AL.dtos.users;


import com.groupe.projet_android_AL.auth.records.TokenPair;
import com.groupe.projet_android_AL.models.Users;

public record UsersLoginResponseDTO(
        Users user,
        TokenPair tokenPair
) {}
