package com.example.EduTask.service.impl;

import com.example.EduTask.domain.exceptions.ResourceNotFoundExceptions;
import com.example.EduTask.domain.tasks.Status;
import com.example.EduTask.domain.tasks.Task;
import com.example.EduTask.domain.tasks.TaskStatus;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.repository.TaskRepository;
import com.example.EduTask.repository.TaskStatusesRepository;
import com.example.EduTask.service.GroupService;
import com.example.EduTask.service.GroupUserService;
import com.example.EduTask.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final GroupService groupService;
    private final GroupUserService groupUserService;
    private final TaskStatusesRepository taskStatusesRepository;


    @Transactional
    @Override
    public TaskStatus createTask(final Task task) {

        // Получаем список пользователей, входящих в группу
        List<User> users = groupUserService.getUsersInGroup(task.getGroup().getId());

        // Сохраняем задачу
        taskRepository.save(task);

        // Создаём TaskStatus для каждого пользователя
        List<TaskStatus> taskStatuses = users.stream()
                .map(user -> {
                    TaskStatus taskStatus = new TaskStatus();
                    taskStatus.setTask(task); // Связываем статус с задачей
                    taskStatus.setUser(user); // Связываем статус с пользователем
                    taskStatus.setStatus(Status.NEW); // Устанавливаем статус "NEW"
                    taskStatus.setUpdatedAt(LocalDateTime.now()); // Устанавливаем текущее время
                    return taskStatus;
                })
                .collect(Collectors.toList());

        // Сохраняем все статусы
        taskStatusesRepository.saveAllAndFlush(taskStatuses);

        // Возвращаем сохранённую задачу
        return getTaskStatusByTaskAndUser(task.getId(), task.getCreator().getId());
    }


    @Override
    public Task getTaskById(final Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptions("Task not found."));
    }


    @Transactional
    @Override
    public TaskStatus updateTask(final Task task) {

        Task existing = getTaskById(task.getId());

        existing.setTitle(task.getTitle());

        existing.setDescription(task.getDescription());

        existing.setDeadline(task.getDeadline());

        taskRepository.save(existing);

        List<TaskStatus> taskStatuses = getTaskStatusesByTaskId(task.getId());

        taskStatuses.forEach(taskStatus -> {
            taskStatus.setStatus(taskStatus.getStatus());

            taskStatus.setUpdatedAt(LocalDateTime.now());
        });

        return null;
    }

    @Transactional
    @Override
    public TaskStatus updateTaskStatus(TaskStatus taskStatus) {


        TaskStatus existingTaskStatus = getTaskStatusByTaskAndUser(taskStatus.getTask().getId(), taskStatus.getUser().getId());

        existingTaskStatus.setStatus(taskStatus.getStatus());

        existingTaskStatus.setUpdatedAt(LocalDateTime.now());

        return existingTaskStatus;
    }


    @Transactional
    @Override
    public void deleteTask(final Long id) {
        taskRepository.deleteById(id);

    }


    @Override
    public List<TaskStatus> getTasksByGroupId(final Long groupId, final Long userId) {
        // Получаем список задач по groupId
        List<Task> tasks = taskRepository.findTasksByGroup(groupId);

        // Получаем статусы задач по taskId и userId

        return tasks.stream()
                .flatMap(task -> taskStatusesRepository.findByTaskIdAndUserId(task.getId(), userId).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskStatus> getTaskStatusesByTaskId(final Long taskId) {
        return taskStatusesRepository.findByTaskId(taskId);
    }


    @Override
    public List<TaskStatus> getTasksByUserId(final Long userId) {

        return taskStatusesRepository.findByUserId(userId);
    }

    @Override
    public TaskStatus getTaskStatusByTaskAndUser(final Long taskId, final Long userId) {
        return taskStatusesRepository.findByTaskIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("TaskStatus not found"));
    }
}
