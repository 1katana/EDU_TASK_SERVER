package com.example.EduTask.web.controller;


import com.example.EduTask.domain.tasks.Task;
import com.example.EduTask.domain.tasks.TaskStatus;
import com.example.EduTask.service.GroupService;
import com.example.EduTask.service.TaskService;
import com.example.EduTask.service.TaskStatusService;
import com.example.EduTask.service.UserService;
import com.example.EduTask.web.dto.tasks.TaskDto;
import com.example.EduTask.web.dto.tasks.TaskStatusDto;


import com.example.EduTask.web.dto.validation.OnCreate;
import com.example.EduTask.web.dto.validation.OnUpdate;
import com.example.EduTask.web.mappers.TaskMapper;
import com.example.EduTask.web.mappers.TaskStatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final GroupService groupService;
    private final TaskStatusService taskStatusService;
    private final TaskMapper taskMapper;
    private final TaskStatusMapper taskStatusMapper;

    @PreAuthorize("@customSecurityException.canAccessUser(#taskDto.creatorId)")
    @PostMapping("")
    public TaskStatusDto createTask(@Validated(OnCreate.class) @RequestBody TaskDto taskDto) {

        Task task = taskMapper.toEntity(taskDto);

        return taskStatusMapper.toDto(taskService.createTask(task));
    }

    @PreAuthorize("@customSecurityException.canAccessUser(#taskDto.creatorId)")
    @PutMapping("")
    public void updateTask(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {

        Task task = taskMapper.toEntity(taskDto);


        taskStatusMapper.toDto(taskService.updateTask(task));
    }

    @PreAuthorize("@customSecurityException.canAccessUser(#taskStatusDto.userId)")
    @PutMapping("/status")
    public TaskStatusDto updateTaskStatus(@Validated(OnUpdate.class) @RequestBody TaskStatusDto taskStatusDto) {

        return taskStatusMapper.toDto(taskService.updateTaskStatus(taskStatusMapper.toEntity(taskStatusDto)));
    }

    @PreAuthorize("@customSecurityException.canAccessDeleteTask(#id)")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PreAuthorize("@customSecurityException.canAccessUser(#userId)")
    @GetMapping("/{taskId}/user/{userId}")
    public TaskStatusDto getTaskStatusByTaskAndUser(@PathVariable Long taskId, @PathVariable Long userId) {
        TaskStatus taskStatus = taskService.getTaskStatusByTaskAndUser(taskId, userId);
        return taskStatusMapper.toDto(taskStatus);
    }

}
