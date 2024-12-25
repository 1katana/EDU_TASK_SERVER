package com.example.EduTask.domain.tasks;


import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private  String description;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "group_id", updatable = false, nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name="creator_id", updatable = false, nullable = false)
    private User creator;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt=LocalDateTime.now();
}
