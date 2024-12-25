package com.example.EduTask.web.sequrity;


import com.example.EduTask.domain.users.User;

public final class JwtEntityFactory {

    public static JwtEntity create(final User user){

        return new JwtEntity(user.getId(), user.getEmail(), user.getName(), user.getPassword());
    }



}
