package com.example.EduTask.web.mappers;

import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.service.GroupService;
import com.example.EduTask.service.UserService;
import com.example.EduTask.web.dto.groups.GroupDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GroupMapper {

    private final UserService userService;

    // Mapping from Group to GroupDto
    public GroupDto toDto(Group group) {
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
    public Group toEntity(GroupDto groupDto) {
        if (groupDto == null) {
            return null;
        }

        Group group = new Group();
        group.setId(groupDto.getId());
        group.setName(groupDto.getName());


        group.setCreator(userService.getById(groupDto.getCreatorId()));

//        group.setCreatedAt(groupDto.getCreatedAt() != null ? groupDto.getCreatedAt() : LocalDateTime.now());

        return group;
    }

    // Mapping from List<Group> to List<GroupDto>
    public List<GroupDto> toDtoList(List<Group> groups) {
        if (groups == null) {
            return null;
        }

        return groups.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Mapping from List<GroupDto> to List<Group>
    public List<Group> toEntityList(List<GroupDto> groupDtos) {
        if (groupDtos == null) {
            return null;
        }

        return groupDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}

