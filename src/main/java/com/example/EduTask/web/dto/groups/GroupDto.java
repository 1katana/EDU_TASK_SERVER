package com.example.EduTask.web.dto.groups;



import com.example.EduTask.web.dto.validation.OnCreate;
import com.example.EduTask.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class GroupDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Name must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Size(max = 255, message = "Name length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "Creator must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private Long creatorId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm"
    )
    private LocalDateTime createdAt;
}
