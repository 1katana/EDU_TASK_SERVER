package com.example.EduTask.web.dto.tasks;


import com.example.EduTask.domain.tasks.Status;
import com.example.EduTask.web.dto.validation.OnCreate;
import com.example.EduTask.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskStatusDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    //    @NotNull(message = "Task ID must be not null.", groups = {OnCreate.class, OnUpdate.class})
//    private Long taskId;
    @NotNull(message = "Task must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private TaskDto task;

    @NotNull(message = "User ID must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private Long userId;

    @NotNull(message = "Status must be not null.", groups = OnUpdate.class)
    private Status status = Status.NEW; // Устанавливаем значение по умолчанию

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm"
    )
    private LocalDateTime updatedAt;
}