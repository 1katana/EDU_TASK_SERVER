package com.example.EduTask.web.mappers;


import com.example.EduTask.domain.tasks.Task;
import com.example.EduTask.domain.tasks.TaskStatus;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.service.UserService;
import com.example.EduTask.web.dto.tasks.TaskDto;
import com.example.EduTask.web.dto.tasks.TaskStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskStatusMapper {

    private final TaskMapper taskMapper;
    private final UserService userService;

    // Mapping from TaskStatus to TaskStatusDto
    public TaskStatusDto toDto(TaskStatus taskStatus) {
        if (taskStatus == null) {
            return null;
        }

        TaskStatusDto taskStatusDto = new TaskStatusDto();
        taskStatusDto.setId(taskStatus.getId());

        TaskDto taskDto=taskMapper.toDto(taskStatus.getTask());

        taskStatusDto.setTask(taskDto); // Convert Task to TaskDto
//        taskStatusDto.getTask().setId(taskStatus.getTask().getId()); // Set Task ID
        taskStatusDto.setUserId(taskStatus.getUser().getId());
        taskStatusDto.setStatus(taskStatus.getStatus());
        taskStatusDto.setUpdatedAt(taskStatus.getUpdatedAt());

        return taskStatusDto;
    }

    // Mapping from TaskStatusDto to TaskStatus
    public TaskStatus toEntity(TaskStatusDto taskStatusDto) {
        if (taskStatusDto == null) {
            return null;
        }

        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setId(taskStatusDto.getId());


        taskStatus.setTask(taskMapper.toEntity(taskStatusDto.getTask()));


        taskStatus.setUser(userService.getById(taskStatusDto.getUserId()));

        taskStatus.setStatus(taskStatusDto.getStatus());
//        taskStatus.setUpdatedAt(taskStatusDto.getUpdatedAt() != null ? taskStatusDto.getUpdatedAt() : LocalDateTime.now());

        return taskStatus;
    }

    // Mapping from List<TaskStatus> to List<TaskStatusDto>
    public List<TaskStatusDto> toDtoList(List<TaskStatus> taskStatuses) {
        if (taskStatuses == null) {
            return null;
        }

        return taskStatuses.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Mapping from List<TaskStatusDto> to List<TaskStatus>
    public List<TaskStatus> toEntityList(List<TaskStatusDto> taskStatusDtos) {
        if (taskStatusDtos == null) {
            return null;
        }

        return taskStatusDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
