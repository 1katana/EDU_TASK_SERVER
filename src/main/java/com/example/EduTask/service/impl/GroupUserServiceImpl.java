package com.example.EduTask.service.impl;

import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.groups.GroupUserId;
import com.example.EduTask.domain.groups.GroupUsers;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.repository.GroupUserRepository;
import com.example.EduTask.service.GroupService;
import com.example.EduTask.service.GroupUserService;
import com.example.EduTask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupUserServiceImpl implements GroupUserService {

    private final UserService userService;
    private final GroupService groupService;
    private final GroupUserRepository groupUserRepository;



    @Transactional
    @Override
    public void addUserToGroup(final Long groupId, final Long userId) {
        User userExisting = userService.getById(userId);
        Group groupExisting = groupService.getGroupById(groupId);

        GroupUserId groupUserId = new GroupUserId(userId,groupId);

        groupUserRepository.save(new GroupUsers(groupUserId, groupExisting, userExisting));

    }

    @Transactional
    @Override
    public void removeUserFromGroup(final Long groupId, final Long userId) {
        User userExisting = userService.getById(userId);
        Group groupExisting = groupService.getGroupById(groupId);

        GroupUserId groupUserId = new GroupUserId(userId,groupId);

        groupUserRepository.deleteById(groupUserId);
    }

    @Override
    public List<User> getUsersInGroup(Long groupId) {
        Group groupExisting = groupService.getGroupById(groupId);

        return groupUserRepository.findUsersByGroupId(groupId);
    }

    @Override
    public List<Group> getGroupsByUserId(Long userId) {
        User userExisting = userService.getById(userId);

        return groupUserRepository.findGroupsByUserId(userId);
    }
}
