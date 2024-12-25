package com.example.EduTask.repository;

import com.example.EduTask.domain.tasks.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskStatusesRepository extends JpaRepository<TaskStatus, Long> {

    @Query(value = "SELECT * FROM task_statuses ts WHERE ts.task_id = :taskId", nativeQuery = true)
    List<TaskStatus> findByTaskId(@Param("taskId") Long taskId);

    @Query(value = "SELECT * FROM task_statuses ts WHERE ts.task_id = :taskId AND ts.user_id = :userId", nativeQuery = true)
    Optional<TaskStatus> findByTaskIdAndUserId(@Param("taskId") Long taskId, @Param("userId") Long userId);

    @Query(value = "SELECT * FROM task_statuses ts WHERE ts.user_id = :userId", nativeQuery = true)
    List<TaskStatus> findByUserId(@Param("userId") Long userId);

}