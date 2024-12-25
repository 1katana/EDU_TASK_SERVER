package com.example.EduTask.domain.groups;


import com.example.EduTask.domain.users.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data

@NoArgsConstructor
@AllArgsConstructor
public class GroupUserId {



    @Column(name = "user_id")
    private Long userId;

    @Column(name = "group_id")
    private Long groupId;
}
