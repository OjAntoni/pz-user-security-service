package com.example.pzusersecurityservice.repository;

import com.example.pzusersecurityservice.model.UserSecret;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserSecretRepository extends JpaRepository<UserSecret, UUID> {
    UserSecret findByDetailsId(UUID id);
}
