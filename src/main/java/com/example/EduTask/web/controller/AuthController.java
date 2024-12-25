package com.example.EduTask.web.controller;


import com.example.EduTask.domain.users.User;
import com.example.EduTask.service.AuthService;
import com.example.EduTask.service.UserService;
import com.example.EduTask.web.dto.auth.JwtRequest;
import com.example.EduTask.web.dto.auth.JwtResponse;
import com.example.EduTask.web.dto.users.UserDto;
import com.example.EduTask.web.dto.validation.OnCreate;
import com.example.EduTask.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody final JwtRequest loginRequest) {

        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody final UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        User createdUser = userService.create(user);

        return UserMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody final String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
