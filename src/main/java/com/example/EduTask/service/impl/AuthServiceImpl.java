package com.example.EduTask.service.impl;

import com.example.EduTask.domain.users.User;
import com.example.EduTask.service.AuthService;
import com.example.EduTask.service.UserService;
import com.example.EduTask.web.dto.auth.JwtRequest;
import com.example.EduTask.web.dto.auth.JwtResponse;
import com.example.EduTask.web.sequrity.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public JwtResponse login(JwtRequest loginRequest) {

        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()
                )
        );
        User user = userService.getByEmail(loginRequest.getEmail());
        jwtResponse.setId(user.getId());
        jwtResponse.setEmail(user.getEmail());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(),user.getEmail()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(),user.getEmail()));

        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
