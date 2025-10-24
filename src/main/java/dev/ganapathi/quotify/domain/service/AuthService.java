package dev.ganapathi.quotify.domain.service;

import dev.ganapathi.quotify.api.dto.user.UserResponse;
import dev.ganapathi.quotify.api.mapper.UserMapper;
import dev.ganapathi.quotify.domain.model.User;
import dev.ganapathi.quotify.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse register(User user){
        checkUserAvailable(user);
        if(user.getId() == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userMapper.toResponse(userRepository.save(user));
    }

    private void checkUserAvailable(User user){
        var existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent() && !existingUser.get().equals(user)){
            throw new RuntimeException("User already exists");
        }
    }
}
