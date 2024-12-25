package com.example.EduTask.web.dto.users;


import com.example.EduTask.web.dto.validation.OnCreate;
import com.example.EduTask.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;



@Getter
@Setter
public class UserDto{

    @NotNull(
            message = "Id must be not null.",
            groups = OnUpdate.class
    )
    private Long id;


    @NotNull(
            message = "Name must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Length(
            max = 255,
            message = "Name length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String name;


    @Length(
            max = 255,
            message = "lastName length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String lastName;


    @NotNull(
            message = "Email must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Length(
            max = 255,
            message = "Email length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String email;


    @JsonProperty(
            access = JsonProperty.Access.WRITE_ONLY
    )
    @NotNull(
            message = "Password must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String password;


    @JsonProperty(
            access = JsonProperty.Access.WRITE_ONLY
    )
    @NotNull(
            message = "Password confirmation must be not null.",
            groups = {OnCreate.class}
    )
    private String passwordConfirmations;

}
