package com.groupe.projet_android_AL.auth;

import com.groupe.projet_android_AL.auth.annotations.CurrentUser;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.repositories.UsersRepository;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UsersRepository usersRepository;

    public CurrentUserArgumentResolver(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && Users.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();
        if (principal == null) {
            throw new AuthenticationCredentialsNotFoundException("Authentication principal is null");
        }

        String username = null;
        try {
            username = (String) principal.getClass().getMethod("getUsername").invoke(principal);
        } catch (Exception e) {
            username = principal.toString();
        }

        Integer userId;
        try {
            userId = Integer.valueOf(username);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Principal username is not a valid user id: " + username);
        }

        Optional<Users> u = usersRepository.findById(userId);
        return u.orElseThrow(() -> new IllegalArgumentException("User not found for id: " + userId));
    }
}

