package com.example.EduTask.service;

import com.example.EduTask.domain.tasks.Task;
import com.example.EduTask.domain.tasks.TaskStatus;

import java.util.List;

public interface TaskService {

    // Создание задачи
    TaskStatus createTask(Task task);

    // Получение задачи по ID
    Task getTaskById(Long id);

    // Обновление задачи
    TaskStatus updateTask(Task task);

    // Обновление статуса задачи
    TaskStatus updateTaskStatus(TaskStatus taskStatus);

    // Удаление задачи
    void deleteTask(Long id);

    // Получение статусов задач по ID группы и ID пользователя
    List<TaskStatus> getTasksByGroupId(Long groupId, Long userId);

    // Получение статусов задач по ID задачи
    List<TaskStatus> getTaskStatusesByTaskId(Long taskId);

    // Получение статусов задач по ID пользователя
    List<TaskStatus> getTasksByUserId(Long userId);

    // Получение статуса задачи по ID задачи и ID пользователя
    TaskStatus getTaskStatusByTaskAndUser(Long taskId, Long userId);
}