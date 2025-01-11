package com.example.EduTask.service.impl;

import com.example.EduTask.domain.exceptions.ResourceNotFoundExceptions;
import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.groups.GroupUserId;
import com.example.EduTask.domain.groups.GroupUsers;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.repository.GroupRepository;
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
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupUserService groupUserService;


    @Transactional
    @Override
    public Group createGroup(final Group group) {
        groupRepository.save(group);
        groupUserService.addUserToGroup(group.getId(),group.getCreator().getId());
        return group;
    }

    @Override
    public Group getGroupById(final Long id) {
        return groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptions("Group not found"));
    }

    @Transactional
    @Override
    public Group updateGroup(final Group group) {

        Group existing=getGroupById(group.getId());

        existing.setName(group.getName());

        groupRepository.save(existing);

        return group;
    }

    @Transactional
    @Override
    public void deleteGroup(final Long id) {
        groupRepository.deleteById(id);
    }



}
