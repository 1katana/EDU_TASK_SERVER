package com.example.EduTask.web.controller;


import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.service.*;
import com.example.EduTask.web.dto.groups.GroupDto;
import com.example.EduTask.web.dto.tasks.TaskStatusDto;
import com.example.EduTask.web.dto.users.UserDto;
//import com.example.EduTask.web.mappers.TaskMapper;
import com.example.EduTask.web.dto.validation.OnUpdate;
import com.example.EduTask.web.mappers.GroupMapper;
import com.example.EduTask.web.mappers.TaskStatusMapper;
import com.example.EduTask.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final GroupMapper groupMapper;

    private final TaskStatusMapper taskStatusMapper;


    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityException.canAccessUser(#id)")

    public UserDto getById(
            @PathVariable final Long id
    ) {

        return UserMapper.toDto(userService.getById(id));
    }

    @PreAuthorize("@customSecurityException.canAccessUser(#id)")
    @DeleteMapping("/{id}")
    public void deleteById(
            @PathVariable final Long id
    ) {
        userService.delete(id);
    }

    @PreAuthorize("@customSecurityException.canAccessUser(#id)")
    @GetMapping("/{id}/groups")
    public List<GroupDto> getUserGroups(@PathVariable final Long id) {

        return groupMapper.toDtoList(groupUserService.getGroupsByUserId(id));
    }


    @PreAuthorize("@customSecurityException.canAccessUserAndGroup(#id,#groupId)")
    @GetMapping("/{id}/group/{groupId}")
    public GroupDto getGroupByUserIdAndGroupId(@PathVariable final Long id, @PathVariable final Long groupId) {

        return groupMapper.toDto(groupUserService.getGroupByUserIdAndGroupId(id,groupId));
    }


    @PreAuthorize("@customSecurityException.canAccessUser(#userDto.id)")
    @PutMapping
    public UserDto update(
            @Validated(OnUpdate.class) @RequestBody final UserDto userDto
    ) {
        User user = UserMapper.toEntity(userDto);

        User updateUser = userService.update(user);
        return UserMapper.toDto(updateUser);
    }

    @PreAuthorize("@customSecurityException.canAccessUser(#id)")
    @GetMapping("/{id}/tasks")
    public List<TaskStatusDto> getUserTasks(@PathVariable final Long id) {

        return taskStatusMapper.toDtoList(taskService.getTasksByUserId(id));
    }


    @GetMapping("/find/{snippetEmail}")
    public List<User> findUserBySnippetEmail(@PathVariable final String snippetEmail) {

        return userService.findByEmail(snippetEmail);
    }


}
