package com.example.pzusersecurityservice.service;

import com.example.pzusersecurityservice.web.dto.UserDetailsResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-details-service", url = "${user-details-service.url}")
public interface UserDetailsService {

    @GetMapping("/api/v1/user/{id}")
    UserDetailsResponseDto getUserDetailsById(@PathVariable UUID id);

    @DeleteMapping("/api/v1/user/{id}")
    void deleteUserDetails(@PathVariable UUID id);
}
