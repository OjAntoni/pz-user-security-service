package com.example.pzusersecurityservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlaceResponseDto {
    private long id;
    private String faculty;
    private String specialization;
}
