package com.example.EduTask.web.controller;


import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.groups.GroupUsers;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.service.GroupService;
import com.example.EduTask.service.GroupUserService;
import com.example.EduTask.service.UserService;
import com.example.EduTask.web.dto.groups.GroupDto;
import com.example.EduTask.web.dto.users.UserDto;
import com.example.EduTask.web.dto.validation.OnCreate;
import com.example.EduTask.web.dto.validation.OnUpdate;
import com.example.EduTask.web.mappers.GroupMapper;
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
    private final UserService userService;
    private final GroupUserService groupUserService;



    @GetMapping("/{id}")
    public GroupDto getGroupById(@PathVariable final Long id) {
        return GroupMapper.toDto(groupService.getGroupById(id));
    }

    @PostMapping("")
    public GroupDto createGroup(@Validated(OnCreate.class) @RequestBody GroupDto groupDto) {
        Group group = GroupMapper.toEntity(groupDto);
        Group groupNew = groupService.createGroup(group);

        return GroupMapper.toDto(groupNew);
    }

    @PutMapping("")
    public GroupDto updateGroup(@Validated(OnUpdate.class) @RequestBody GroupDto groupDto) {
        Group group = GroupMapper.toEntity(groupDto);
        Group groupNew = groupService.updateGroup(group);

        return GroupMapper.toDto(groupNew);
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
