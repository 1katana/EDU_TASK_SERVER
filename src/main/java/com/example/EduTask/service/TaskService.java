package com.example.EduTask.service;

import com.example.EduTask.domain.tasks.Task;
import com.example.EduTask.domain.tasks.TaskStatus;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    Task getTaskById(Long id);
    Task updateTask(Task task);
    void deleteTask(Long id);

    List<Task> getTasksByGroupId(Long groupId);

    List<TaskStatus> getTasksByUserId(Long userId);
}
