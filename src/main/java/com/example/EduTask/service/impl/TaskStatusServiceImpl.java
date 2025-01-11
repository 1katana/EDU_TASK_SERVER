package com.example.EduTask.service.impl;

import com.example.EduTask.domain.exceptions.ResourceNotFoundExceptions;
import com.example.EduTask.domain.tasks.Status;
import com.example.EduTask.domain.tasks.Task;
import com.example.EduTask.domain.tasks.TaskStatus;
import com.example.EduTask.domain.users.User;
import com.example.EduTask.repository.TaskStatusesRepository;
import com.example.EduTask.service.TaskService;
import com.example.EduTask.service.TaskStatusService;
import com.example.EduTask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskService taskService;
    private final TaskStatusesRepository taskStatusesRepository;
    private final UserService userService;

    @Transactional
    @Override
    public TaskStatus createTaskStatus(final Long taskId, final Long userId, TaskStatus taskStatus) {

        User userExisting = userService.getById(userId);
        Task taskExisting= taskService.getTaskById(taskId);

        taskStatus.setStatus(Status.NEW);

        taskStatusesRepository.save(taskStatus);
        

        return taskStatus;
    }


    @Transactional
    @Override
    public TaskStatus updateTaskStatus(final Long taskId, final Long userId, final TaskStatus taskStatus) {

        TaskStatus existingTaskStatus = getTaskStatusByTaskAndUser(taskId,userId);

        existingTaskStatus.setStatus(taskStatus.getStatus());

        return taskStatus;
    }


    @Override
    public List<TaskStatus> getTaskStatusesByTaskId(final Long taskId) {
        return taskStatusesRepository.findByTaskId(taskId);
    }

    @Override
    public TaskStatus getTaskStatusByTaskAndUser(final Long taskId, final Long userId) {
        return taskStatusesRepository.findByTaskIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("TaskStatus not found"));
    }
}
