package com.example.EduTask.web.controller;


import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.tasks.TaskStatus;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.service.GroupService;
import com.example.EduTask.service.GroupUserService;
import com.example.EduTask.service.TaskService;
import com.example.EduTask.web.dto.groups.GroupDto;
import com.example.EduTask.web.dto.tasks.TaskStatusDto;
import com.example.EduTask.web.dto.users.UserDto;
import com.example.EduTask.web.dto.validation.OnCreate;
import com.example.EduTask.web.dto.validation.OnUpdate;
import com.example.EduTask.web.mappers.GroupMapper;
import com.example.EduTask.web.mappers.TaskStatusMapper;
import com.example.EduTask.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
@Validated

public class GroupController {

    private final GroupService groupService;
    private final GroupUserService groupUserService;
    private final GroupMapper groupMapper;
    private final TaskStatusMapper taskStatusMapper;
    private final TaskService taskService;



    @GetMapping("/{id}")
    public GroupDto getGroupById(@PathVariable final Long id) {
        return groupMapper.toDto(groupService.getGroupById(id));
    }

    @PostMapping("")
    public GroupDto createGroup(@Validated(OnCreate.class) @RequestBody GroupDto groupDto) {
        Group group = groupMapper.toEntity(groupDto);
        Group groupNew = groupService.createGroup(group);

        return groupMapper.toDto(groupNew);
    }

    @PutMapping("")
    public GroupDto updateGroup(@Validated(OnUpdate.class) @RequestBody GroupDto groupDto) {
        Group group = groupMapper.toEntity(groupDto);
        Group groupNew = groupService.updateGroup(group);

        return groupMapper.toDto(groupNew);
    }

    @GetMapping("/{groupId}/user/{userId}")
    public List<TaskStatusDto> getTasksByGroupId(@PathVariable Long groupId, @PathVariable Long userId) {
        List<TaskStatus> taskStatuses = taskService.getTasksByGroupId(groupId, userId);
        return taskStatusMapper.toDtoList(taskStatuses);
    }

    @GetMapping("/{id}/users")
    public List<UserDto> getUsersInGroup(@PathVariable final Long id) {
        // Получаем список пользователей в группе
        List<User> usersInGroup = groupUserService.getUsersInGroup(id);

        // Преобразуем их в DTO
        return UserMapper.toDtoList(usersInGroup);
    }

    @PostMapping("/{groupId}/users/{userId}")
    public ResponseEntity<String> addUserToGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {
        groupUserService.addUserToGroup(groupId, userId);
        return ResponseEntity.ok("User added to group successfully");
    }
}
