package com.example.EduTask.web.mappers;


import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.tasks.Task;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.web.dto.tasks.TaskDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    // Mapping from Task to TaskDto
    public static TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }

        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setDeadline(task.getDeadline());
        taskDto.setGroupId(task.getGroup() != null ? task.getGroup().getId() : null);
        taskDto.setCreatorId(task.getCreator() != null ? task.getCreator().getId() : null);
        taskDto.setCreatedAt(task.getCreatedAt());

        return taskDto;
    }

    // Mapping from TaskDto to Task
    public static Task toEntity(TaskDto taskDto) {
        if (taskDto == null) {
            return null;
        }

        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDeadline(taskDto.getDeadline());

        // Assuming you can get Group and User by their IDs (usually through a service)
        Group group = new Group();
        group.setId(taskDto.getGroupId());  // You can fetch the group by its ID from the database
        task.setGroup(group);

        User creator = new User();
        creator.setId(taskDto.getCreatorId());  // You can fetch the user by its ID from the database
        task.setCreator(creator);

        task.setCreatedAt(taskDto.getCreatedAt() != null ? taskDto.getCreatedAt() : LocalDateTime.now());

        return task;
    }

    // Mapping from List<Task> to List<TaskDto>
    public static List<TaskDto> toDtoList(List<Task> tasks) {
        if (tasks == null) {
            return null;
        }

        return tasks.stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    // Mapping from List<TaskDto> to List<Task>
    public static List<Task> toEntityList(List<TaskDto> taskDtos) {
        if (taskDtos == null) {
            return null;
        }

        return taskDtos.stream()
                .map(TaskMapper::toEntity)
                .collect(Collectors.toList());
    }
}
