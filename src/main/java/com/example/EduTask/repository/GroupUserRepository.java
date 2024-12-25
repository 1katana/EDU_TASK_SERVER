package com.example.EduTask.repository;

import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.groups.GroupUserId;
import com.example.EduTask.domain.groups.GroupUsers;
import com.example.EduTask.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupUserRepository extends JpaRepository<GroupUsers, GroupUserId> {

//    @Query(value = "SELECT u FROM group_users gu JOIN gu.user u WHERE gu.id.groupId = :groupId", nativeQuery = true)
//    List<User> findUsersByGroupId(@Param("groupId") Long groupId);
//
//    @Query(value = "SELECT g FROM group_users gu JOIN gu.group g WHERE gu.id.userId = :userId", nativeQuery = true)
//    List<Group> findGroupsByUserId(@Param("userId") Long userId);
//

//    @Query(value = "SELECT u\n" +
//            "FROM users u\n" +
//            "JOIN group_users gu ON u.id = gu.user_id\n" +
//            "WHERE gu.group_id = :groupId;", nativeQuery = true)
//    List<Object[]> findUsersByGroupId(@Param("groupId") Long groupId);

    @Query("SELECT u FROM User u " +
            "JOIN GroupUsers gu ON u.id = gu.user.id " +
            "WHERE gu.group.id = :groupId")
    List<User> findUsersByGroupId(@Param("groupId") Long groupId);


    @Query("SELECT gu.group FROM GroupUsers gu WHERE gu.user.id = :userId")
    List<Group> findGroupsByUserId(@Param("userId") Long userId);

    @Query("SELECT gu.group FROM GroupUsers gu WHERE gu.user.id = :userId AND gu.group.id = :groupId")
    Optional<Group> findGroupByUserIdAndGroupId(@Param("userId") Long userId, @Param("groupId") Long groupId);

}
