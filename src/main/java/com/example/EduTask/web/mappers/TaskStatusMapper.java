package com.example.EduTask.web.mappers;


import com.example.EduTask.domain.tasks.Task;
import com.example.EduTask.domain.tasks.TaskStatus;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.web.dto.tasks.TaskDto;
import com.example.EduTask.web.dto.tasks.TaskStatusDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TaskStatusMapper {



    // Mapping from TaskStatus to TaskStatusDto
    public static TaskStatusDto toDto(TaskStatus taskStatus) {
        if (taskStatus == null) {
            return null;
        }

        TaskStatusDto taskStatusDto = new TaskStatusDto();
        taskStatusDto.setId(taskStatus.getId());

        TaskDto taskDto=TaskMapper.toDto(taskStatus.getTask());

        taskStatusDto.setTask(taskDto); // Convert Task to TaskDto
//        taskStatusDto.getTask().setId(taskStatus.getTask().getId()); // Set Task ID
        taskStatusDto.setUserId(taskStatus.getUser() != null ? taskStatus.getUser().getId() : null);
        taskStatusDto.setStatus(taskStatus.getStatus());
        taskStatusDto.setUpdatedAt(taskStatus.getUpdatedAt());

        return taskStatusDto;
    }

    // Mapping from TaskStatusDto to TaskStatus
    public static TaskStatus toEntity(TaskStatusDto taskStatusDto) {
        if (taskStatusDto == null) {
            return null;
        }

        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setId(taskStatusDto.getId());

        // Convert TaskDto to Task
        Task task = TaskMapper.toEntity(taskStatusDto.getTask());
        taskStatus.setTask(task);

        // Convert User ID to User entity
        User user = new User();
        user.setId(taskStatusDto.getUserId());
        taskStatus.setUser(user);

        taskStatus.setStatus(taskStatusDto.getStatus());
        taskStatus.setUpdatedAt(taskStatusDto.getUpdatedAt() != null ? taskStatusDto.getUpdatedAt() : LocalDateTime.now());

        return taskStatus;
    }

    // Mapping from List<TaskStatus> to List<TaskStatusDto>
    public static List<TaskStatusDto> toDtoList(List<TaskStatus> taskStatuses) {
        if (taskStatuses == null) {
            return null;
        }

        return taskStatuses.stream()
                .map(TaskStatusMapper::toDto)
                .collect(Collectors.toList());
    }

    // Mapping from List<TaskStatusDto> to List<TaskStatus>
    public static List<TaskStatus> toEntityList(List<TaskStatusDto> taskStatusDtos) {
        if (taskStatusDtos == null) {
            return null;
        }

        return taskStatusDtos.stream()
                .map(TaskStatusMapper::toEntity)
                .collect(Collectors.toList());
    }
}
