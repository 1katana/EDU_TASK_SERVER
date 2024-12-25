package com.example.EduTask.web.mappers;

import com.example.EduTask.domain.groups.GroupUserId;
import com.example.EduTask.domain.groups.GroupUsers;
import com.example.EduTask.service.GroupService;
import com.example.EduTask.service.UserService;
import com.example.EduTask.web.dto.groups.GroupUsersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupUserMapper {

    private final GroupService groupService;
    private final UserService userService;

    // Mapping from GroupUsersDto to GroupUserId
    public GroupUsers toEntity(GroupUsersDto groupUsersDto) {
        if (groupUsersDto == null) {
            return null;
        }
        GroupUserId groupUserId = new GroupUserId();
        groupUserId.setUserId(groupUsersDto.getUserId());
        groupUserId.setGroupId(groupUsersDto.getGroupId());

        GroupUsers groupUser = new GroupUsers();
        groupUser.setId(groupUserId);
        groupUser.setUser(userService.getById(groupUsersDto.getUserId()));
        groupUser.setGroup(groupService.getGroupById(groupUsersDto.getGroupId()));

        return groupUser;
    }

    // Mapping from GroupUserId to GroupUsersDto
    public GroupUsersDto toDto(GroupUserId groupUserId) {
        if (groupUserId == null) {
            return null;
        }

        GroupUsersDto groupUsersDto = new GroupUsersDto();
        groupUsersDto.setUserId(groupUserId.getUserId());
        groupUsersDto.setGroupId(groupUserId.getGroupId());

        return groupUsersDto;
    }
}