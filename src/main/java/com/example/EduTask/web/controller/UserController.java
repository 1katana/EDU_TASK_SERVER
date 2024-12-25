package com.example.EduTask.web.controller;


import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.service.GroupService;
import com.example.EduTask.service.GroupUserService;
import com.example.EduTask.service.TaskService;
import com.example.EduTask.service.UserService;
import com.example.EduTask.web.dto.groups.GroupDto;
import com.example.EduTask.web.dto.tasks.TaskStatusDto;
import com.example.EduTask.web.dto.users.UserDto;
//import com.example.EduTask.web.mappers.TaskMapper;
import com.example.EduTask.web.dto.validation.OnUpdate;
import com.example.EduTask.web.mappers.GroupMapper;
import com.example.EduTask.web.mappers.TaskStatusMapper;
import com.example.EduTask.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated

public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    private final GroupUserService groupUserService;

//    private final TaskMapper taskMapper;


    @GetMapping("/{id}")
    public UserDto getById(
            @PathVariable final Long id
    ) {

        return UserMapper.toDto(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(
            @PathVariable final Long id
    ) {
        userService.delete(id);
    }

    @GetMapping("/{id}/groups")
    public List<GroupDto> getUserGroups(@PathVariable final Long id) {

        return GroupMapper.toDtoList(groupUserService.getGroupsByUserId(id));
    }

    @PutMapping
    public UserDto update(
            @Validated(OnUpdate.class) @RequestBody final UserDto userDto
    ){
        User user = UserMapper.toEntity(userDto);
        User updateUser = userService.update(user);
        return UserMapper.toDto(updateUser);
    }

    @GetMapping("/{id}/tasks")
    public List<TaskStatusDto> getUserTasks(@PathVariable final Long id) {

        return TaskStatusMapper.toDtoList(taskService.getTasksByUserId(id));
    }


}
