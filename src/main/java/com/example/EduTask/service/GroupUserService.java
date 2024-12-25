package com.example.EduTask.service;

import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.groups.GroupUserId;
import com.example.EduTask.domain.groups.GroupUsers;
import com.example.EduTask.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupUserService {


    void addUserToGroup(final Long groupId, final Long userId);

    void removeUserFromGroup(final Long groupId, final Long userId);


    List<User> getUsersInGroup(Long groupId);
    List<Group> getGroupsByUserId(Long userId);

    Group getGroupByUserIdAndGroupId(Long userId, Long groupId);
}
