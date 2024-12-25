package com.example.EduTask.service;

import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.users.User;

import java.util.List;

public interface GroupService {

    Group createGroup(Group group);
    Group getGroupById(Long id);
    Group updateGroup( Group group);
    void deleteGroup(Long id);


}