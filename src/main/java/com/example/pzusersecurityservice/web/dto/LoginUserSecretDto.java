package com.example.pzusersecurityservice.web.dto;

import lombok.Data;

@Data
public class LoginUserSecretDto {
    private String password;
    private String username;
}
