package com.example.EduTask.domain.groups;


import com.example.EduTask.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupUsers {

    @EmbeddedId
    private GroupUserId id;

    @ManyToOne
    @MapsId("groupId")  // связываем с группой через составной ключ
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;  // связь с сущностью Group

    @ManyToOne
    @MapsId("userId")  // связываем с учеником через составной ключ
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // связь с сущностью User


}



