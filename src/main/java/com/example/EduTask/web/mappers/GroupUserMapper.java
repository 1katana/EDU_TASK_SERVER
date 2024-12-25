package com.example.EduTask.web.mappers;

import com.example.EduTask.domain.groups.GroupUserId;
import com.example.EduTask.web.dto.groups.GroupUsersDto;

public class GroupUserMapper {

    // Mapping from GroupUsersDto to GroupUserId
    public static GroupUserId toEntity(GroupUsersDto groupUsersDto) {
        if (groupUsersDto == null) {
            return null;
        }

        GroupUserId groupUserId = new GroupUserId();
        groupUserId.setUserId(groupUsersDto.getUserId());
        groupUserId.setGroupId(groupUsersDto.getGroupId());

        return groupUserId;
    }

    // Mapping from GroupUserId to GroupUsersDto
    public static GroupUsersDto toDto(GroupUserId groupUserId) {
        if (groupUserId == null) {
            return null;
        }

        GroupUsersDto groupUsersDto = new GroupUsersDto();
        groupUsersDto.setUserId(groupUserId.getUserId());
        groupUsersDto.setGroupId(groupUserId.getGroupId());

        return groupUsersDto;
    }
}