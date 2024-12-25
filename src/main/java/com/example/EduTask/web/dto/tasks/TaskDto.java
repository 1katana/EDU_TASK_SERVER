package com.example.EduTask.web.dto.tasks;



import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.web.dto.validation.OnCreate;
import com.example.EduTask.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
public class TaskDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Title must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Size(max = 255, message = "Title length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Size(max = 1000, message = "Description length must be smaller than 1000 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm"
    )
    @NotNull(message = "Deadline must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private LocalDateTime deadline;

    @NotNull(message = "Group must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private Long groupId;

    @NotNull(message = "Creator must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private Long creatorId;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
}
