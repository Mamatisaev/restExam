package dortmund.service;

import dortmund.dto.request.UserRequest;
import dortmund.dto.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponse create(UserRequest userRequest);

}