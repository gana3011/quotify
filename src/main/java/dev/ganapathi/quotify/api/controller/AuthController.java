package dev.ganapathi.quotify.api.controller;

import dev.ganapathi.quotify.api.dto.user.UserLogin;
import dev.ganapathi.quotify.api.dto.user.UserRegister;
import dev.ganapathi.quotify.api.dto.user.UserResponse;
import dev.ganapathi.quotify.api.mapper.UserMapper;
import dev.ganapathi.quotify.domain.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserMapper userMapper;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegister register){
        var user = userMapper.toEntity(register);
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserLogin userLogin){
        return ResponseEntity.ok(authService.login(userLogin));
    }
}
