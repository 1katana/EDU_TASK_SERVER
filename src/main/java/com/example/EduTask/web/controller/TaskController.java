package com.example.EduTask.web.controller;


import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.tasks.Task;
import com.example.EduTask.domain.tasks.TaskStatus;
import com.example.EduTask.service.GroupService;
import com.example.EduTask.service.TaskService;
import com.example.EduTask.service.TaskStatusService;
import com.example.EduTask.service.UserService;
import com.example.EduTask.web.dto.groups.GroupDto;
import com.example.EduTask.web.dto.tasks.TaskDto;
import com.example.EduTask.web.dto.tasks.TaskStatusDto;


import com.example.EduTask.web.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final GroupService groupService;
    private final TaskStatusService taskStatusService;


    @PostMapping("")
    public TaskDto createTask(@RequestBody TaskDto taskDto){

        Task task = TaskMapper.toEntity(taskDto);


        taskService.createTask(task);

        return TaskMapper.toDto(task);
    }




}
