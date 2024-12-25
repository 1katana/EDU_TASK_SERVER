package com.example.EduTask.service;

import com.example.EduTask.web.dto.auth.JwtRequest;
import com.example.EduTask.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(
            JwtRequest loginRequest
    );

    JwtResponse refresh(
            String refreshToken
    );


}
