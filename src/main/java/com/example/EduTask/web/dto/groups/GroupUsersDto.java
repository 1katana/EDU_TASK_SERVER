package com.example.EduTask.web.dto.groups;



import com.example.EduTask.web.dto.validation.OnCreate;
import com.example.EduTask.web.dto.validation.OnUpdate;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class GroupUsersDto {

    @NotNull(message = "Group ID must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private Long groupId;

    @NotNull(message = "User ID must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private Long userId;
}



