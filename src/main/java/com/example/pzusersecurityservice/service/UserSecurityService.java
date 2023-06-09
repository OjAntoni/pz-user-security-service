package com.example.pzusersecurityservice.service;

import com.example.pzusersecurityservice.model.Roles;
import com.example.pzusersecurityservice.model.UserSecret;
import com.example.pzusersecurityservice.repository.UserSecretRepository;
import com.example.pzusersecurityservice.security.JwtTokenProvider;
import com.example.pzusersecurityservice.web.dto.UserDetailsResponseDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class UserSecurityService {
    @Autowired
    private UserSecretRepository repository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createJWT(String userSecretId, String role){
        return jwtTokenProvider.createToken(userSecretId, List.of(role));
    }

    public boolean existsUserCredentials(UUID credId){
        UserDetailsResponseDto resp = userDetailsService.getUserDetailsById(credId);
        return resp != null;
    }

    //save or update
    public UserSecret save(UserSecret sec) {
        sec.setPassword(encoder.encode(sec.getPassword()));
        UserSecret byDetailsId = repository.findByDetailsId(sec.getDetailsId());
        if(byDetailsId!=null){
            sec.setId(byDetailsId.getId());
        }
        return repository.save(sec);
    }

    public void deleteUserSecret(UUID id){
        Optional<UserSecret> byId = repository.findById(id);
        if (byId.isEmpty()) return;

        UserDetailsResponseDto resp = userDetailsService.getUserDetailsById(byId.get().getDetailsId());

        if(resp != null){
            userDetailsService.deleteUserDetails(resp.getId());
        }
        repository.deleteById(id);
    }

    public UserSecret getUserSecret(UUID id){
        return repository.findById(id).get();
    }

    public String createJwtTokenAfterLogin(String providedPassword, String username){
        UserDetailsResponseDto u = userDetailsService.getUserDetailsByUsername(username);
        if(u == null) return null;
        UserSecret uSec = repository.findByDetailsId(u.getId());
        if(uSec==null) return null;
        if (passwordEncoder.matches(providedPassword, uSec.getPassword())) {
            return jwtTokenProvider.createToken(uSec.getId().toString(), List.of(uSec.getRole()));
        } else {
            return null;
        }
    }
}
