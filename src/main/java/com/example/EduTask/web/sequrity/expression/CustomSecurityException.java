package com.example.EduTask.web.sequrity.expression;


import com.example.EduTask.domain.groups.Group;
import com.example.EduTask.domain.tasks.Task;
import com.example.EduTask.service.GroupUserService;
import com.example.EduTask.service.TaskService;
import com.example.EduTask.service.UserService;
import com.example.EduTask.web.sequrity.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("customSecurityException")
@RequiredArgsConstructor
public class CustomSecurityException {

    private final GroupUserService groupUserService;
    private final TaskService taskService;

    private Long getAuthenticationUserId(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        JwtEntity user= (JwtEntity) authentication.getPrincipal();

        return user.getId();
    }

    public boolean canAccessUser(Long id){

        Long userId =getAuthenticationUserId();

        return userId.equals(id);
    }

    public boolean canAccessGroup(Long groupId) {
        Long userId = getAuthenticationUserId();

        try {
            Group group = groupUserService.getGroupByUserIdAndGroupId(userId, groupId);
            return group != null;
        } catch (Exception e) {
            // Логируем исключение, если это необходимо
            // logger.error("Group not found for userId: {} and groupId: {}", userId, groupId, e);
            return false;
        }
    }

    public boolean canAccessUserAndGroup(Long id,Long groupId) {
        Long userId = getAuthenticationUserId();

        try {
            Group group = groupUserService.getGroupByUserIdAndGroupId(userId, groupId);
            return group != null & userId.equals(id);
        } catch (Exception e) {
            // Логируем исключение, если это необходимо
            // logger.error("Group not found for userId: {} and groupId: {}", userId, groupId, e);
            return false;
        }
    }

    public boolean canAccessAddUserGroup(Long groupId) {
        Long userId = getAuthenticationUserId();

        try {
            Group group = groupUserService.getGroupByUserIdAndGroupId(userId, groupId);
            return userId.equals(group.getCreator().getId());

        } catch (Exception e) {
            // Логируем исключение, если это необходимо
            // logger.error("Group not found for userId: {} and groupId: {}", userId, groupId, e);
            return false;
        }
    }

    public boolean canAccessDeleteTask(Long taskId) {
        Long userId = getAuthenticationUserId();

        try {
            Task task = taskService.getTaskById(taskId);


            return userId.equals(task.getCreator().getId());

        } catch (Exception e) {
            // Логируем исключение, если это необходимо
            // logger.error("Group not found for userId: {} and groupId: {}", userId, groupId, e);
            return false;
        }
    }



}
