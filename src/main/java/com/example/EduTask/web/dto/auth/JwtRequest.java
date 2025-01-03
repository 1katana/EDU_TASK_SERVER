package com.example.EduTask.web.dto.auth;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {

    @NotNull(message = "Email must be not null")
    private String email;

    @NotNull(message = "Password must be not null")
    private String password;
}
