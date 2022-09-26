package dortmund.controller;

import dortmund.dto.request.UserRequest;
import dortmund.dto.response.LoginResponse;
import dortmund.dto.response.UserResponse;
import dortmund.entity.Login;
import dortmund.entity.User;
import dortmund.jwt.JwtTokenUtil;
import dortmund.repository.UserRepository;
import dortmund.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/jwt")
public class AuthController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final Login login;

    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> getLogin(@RequestBody UserRequest userRequest) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(userRequest.getEmail(),
                            userRequest.getPassword());
            authenticationManager.authenticate(token);
            User user = userRepository.findByEmail(token.getName()).get();
            return ResponseEntity.ok().body(login.toLoginView(jwtTokenUtil.generateToken(user),
                    "Token is successfully generated.", user));
        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(login.toLoginView(" ", "login_failed", null));
        }
    }

    @PostMapping("registration")
    public UserResponse create(@RequestBody UserRequest userRequest) {
        return userService.create(userRequest);
    }

}
