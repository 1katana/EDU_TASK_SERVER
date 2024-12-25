package com.example.EduTask.service.impl;

import com.example.EduTask.domain.exceptions.ResourceNotFoundExceptions;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.repository.UserRepository;
import com.example.EduTask.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getById(final Long id) {

        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundExceptions("User not found.")
        );
    }

    @Override
    public User getByEmail(final String email) {

        return userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundExceptions("User not found.")
        );
    }


    @Override
    @Transactional
    public User update(final User user) {
        User existing = getById(user.getId());

        existing.setName(user.getName());

        existing.setEmail(user.getEmail());

        existing.setLastName(user.getLastName());

        existing.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(existing);
        return existing;
    }

    @Transactional
    @Override
    public User create(final User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalStateException("The user with this email already exists");
        }
        if(!user.getPassword().equals(user.getPasswordConfirmations())){
            throw new IllegalStateException(
                    "Password and password confirmation do not match."
            );
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return user;
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        userRepository.deleteById(id);

    }


    @Override
    public List<User> findByEmail(final String snippetEmail) {
        return List.of();
    }
}
