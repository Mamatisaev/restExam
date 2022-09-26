package dortmund.dto.mapper;

import dortmund.dto.request.UserRequest;
import dortmund.dto.response.UserResponse;
import dortmund.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User mapToEntity(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .isActive(true)
                .created(LocalDateTime.now())
                .build();
    }

    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .created(user.getCreated())
                .isActive(user.isActive())
                .build();
    }
}
