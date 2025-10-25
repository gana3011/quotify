package dev.ganapathi.quotify.domain.service;

import dev.ganapathi.quotify.api.dto.user.UserResponse;
import dev.ganapathi.quotify.api.mapper.UserMapper;
import dev.ganapathi.quotify.domain.model.User;
import dev.ganapathi.quotify.domain.repository.UserRepository;
import dev.ganapathi.quotify.infra.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Transactional
    public UserResponse register(User user){
        checkUserAvailable(user);
        if(user.getUserId() == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        String token = jwtService.generateToken(user.getEmail());
        return userMapper.toResponse(userRepository.save(user), token);
    }

    private void checkUserAvailable(User user){
        var existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent() && !existingUser.get().equals(user)){
            throw new RuntimeException("User already exists");
        }
    }
}
