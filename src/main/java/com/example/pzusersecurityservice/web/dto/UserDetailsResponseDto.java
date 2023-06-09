package com.example.pzusersecurityservice.web.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.net.URL;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsResponseDto {
    private UUID id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private WorkPlaceResponseDto workPlace;
    private URL urlToImage;

}