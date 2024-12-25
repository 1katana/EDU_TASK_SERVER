package com.example.EduTask.service;


import com.example.EduTask.domain.tasks.TaskStatus;

import java.util.List;

public interface TaskStatusService {

    TaskStatus createTaskStatus(Long taskId, Long userId, TaskStatus taskStatus);
    TaskStatus updateTaskStatus(Long taskId, Long userId, TaskStatus taskStatus);
    List<TaskStatus> getTaskStatusesByTaskId(Long taskId);
    TaskStatus getTaskStatusByTaskAndUser(Long taskId, Long userId);
}