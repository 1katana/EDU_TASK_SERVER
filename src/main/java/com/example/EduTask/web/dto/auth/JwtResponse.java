package com.example.EduTask.web.dto.auth;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private Long id;
    private String email;
    private String accessToken;
    private String refreshToken;
}
