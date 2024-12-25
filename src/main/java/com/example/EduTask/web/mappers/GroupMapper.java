package com.example.EduTask.web.mappers;

import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.web.dto.groups.GroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;


import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    // Mapping from Group to GroupDto
    public static GroupDto toDto(Group group) {
        if (group == null) {
            return null;
        }

        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setName(group.getName());
        groupDto.setCreatorId(group.getCreator().getId());
        groupDto.setCreatedAt(group.getCreatedAt());

        return groupDto;
    }

    // Mapping from GroupDto to Group
    public static Group toEntity(GroupDto groupDto) {
        if (groupDto == null) {
            return null;
        }

        Group group = new Group();
        group.setId(groupDto.getId());
        group.setName(groupDto.getName());

        // Assuming you have a User service or some way to get the User by creatorId
        User creator = new User();
        creator.setId(groupDto.getCreatorId());  // You can fetch the creator from the database based on the creatorId
        group.setCreator(creator);

        group.setCreatedAt(groupDto.getCreatedAt() != null ? groupDto.getCreatedAt() : LocalDateTime.now());

        return group;
    }

    // Mapping from List<Group> to List<GroupDto>
    public static List<GroupDto> toDtoList(List<Group> groups) {
        if (groups == null) {
            return null;
        }

        return groups.stream()
                .map(GroupMapper::toDto)
                .collect(Collectors.toList());
    }

    // Mapping from List<GroupDto> to List<Group>
    public static List<Group> toEntityList(List<GroupDto> groupDtos) {
        if (groupDtos == null) {
            return null;
        }

        return groupDtos.stream()
                .map(GroupMapper::toEntity)
                .collect(Collectors.toList());
    }
}

