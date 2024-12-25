package com.example.EduTask.web.mappers;


import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.tasks.Task;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.service.GroupService;
import com.example.EduTask.service.UserService;
import com.example.EduTask.web.dto.tasks.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final GroupService groupService;
    private final UserService userService;

    // Mapping from Task to TaskDto
    public TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }

        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setDeadline(task.getDeadline());
        taskDto.setGroupId(task.getGroup().getId());
        taskDto.setCreatorId(task.getCreator().getId());
        taskDto.setCreatedAt(task.getCreatedAt());

        return taskDto;
    }

    // Mapping from TaskDto to Task
    public Task toEntity(TaskDto taskDto) {
        if (taskDto == null) {
            return null;
        }

        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDeadline(taskDto.getDeadline());


        task.setGroup(groupService.getGroupById(taskDto.getGroupId()));


        task.setCreator(userService.getById(taskDto.getCreatorId()));

//        task.setCreatedAt(taskDto.getCreatedAt() != null ? taskDto.getCreatedAt() : LocalDateTime.now());

        return task;
    }

    // Mapping from List<Task> to List<TaskDto>
    public List<TaskDto> toDtoList(List<Task> tasks) {
        if (tasks == null) {
            return null;
        }

        return tasks.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Mapping from List<TaskDto> to List<Task>
    public List<Task> toEntityList(List<TaskDto> taskDtos) {
        if (taskDtos == null) {
            return null;
        }

        return taskDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
