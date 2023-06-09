package com.example.pzusersecurityservice.security;

import com.example.pzusersecurityservice.model.UserSecret;
import com.example.pzusersecurityservice.repository.UserSecretRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final UserSecretRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<UserSecret> appUser = userRepository.findById(UUID.fromString(username));

        if (appUser.isEmpty()) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        UserSecret us = appUser.get();

        return org.springframework.security.core.userdetails.User//
                .withUsername(username)
                .password(us.getPassword())
                .authorities(us.getRole())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}
