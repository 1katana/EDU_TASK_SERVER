package com.example.EduTask.service;

import com.example.EduTask.domain.users.User;

import java.util.List;

public interface UserService {

    User getById(Long id);

    User getByEmail(String email);

    User update(User user);

    User create(User user);

    void delete(Long id);

    List<User> findByEmail(String snippetEmail);

}
