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
import com.example.EduTask.service.TaskStatusService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
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
    public Task createTask(final Task task) {

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
        return task;
    }

    @Override
    public Task getTaskById(final Long id) {
        return taskRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("Task not found."));
    }

    @Transactional
    @Override
    public Task updateTask(final  Task task) {

        Task existing=getTaskById(task.getId());

        existing.setTitle(task.getTitle());

        existing.setDescription(task.getDescription());

        existing.setDeadline(task.getDeadline());

        taskRepository.save(existing);

        return task;
    }

    @Transactional
    @Override
    public void deleteTask(final Long id) {
        taskRepository.deleteById(id);
    }


    @Override
    public List<Task> getTasksByGroupId(final Long groupId) {
        return taskRepository.findTasksByGroup(groupId);
    }

    @Override
    public List<TaskStatus> getTasksByUserId(final Long userId) {

        return taskStatusesRepository.findByUserId(userId);
    }
}
