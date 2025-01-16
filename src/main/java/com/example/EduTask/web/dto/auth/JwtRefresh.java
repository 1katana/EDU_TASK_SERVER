package com.example.EduTask.web.dto.auth;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class JwtRefresh {

    @NotNull(message = "refreshToken must be not null")
    private String refreshToken;
}
