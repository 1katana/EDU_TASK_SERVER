package com.example.EduTask.repository;

import com.example.EduTask.domain.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query(value = "SELECT * FROM tasks t WHERE t.group_id = :groupId", nativeQuery = true)
    List<Task> findTasksByGroup(@Param("groupId") Long groupId);
}
