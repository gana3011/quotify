package dev.ganapathi.quotify.api.controller;

import dev.ganapathi.quotify.api.dto.user.UserLogin;
import dev.ganapathi.quotify.api.dto.user.UserRegister;
import dev.ganapathi.quotify.api.dto.user.UserResponse;
import dev.ganapathi.quotify.api.mapper.UserMapper;
import dev.ganapathi.quotify.domain.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserMapper userMapper;
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody UserRegister register){
        var user = userMapper.toEntity(register);
        return authService.register(user);
    }

    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody UserLogin userLogin){
        return authService.login(userLogin);
    }
}
