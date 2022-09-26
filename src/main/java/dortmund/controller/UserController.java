package dortmund.controller;

import dortmund.dto.request.UserRequest;
import dortmund.dto.response.UserResponse;
import dortmund.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping
    public UserResponse create(@RequestBody UserRequest userRequest) {
        return userServiceImpl.create(userRequest);
    }

}
