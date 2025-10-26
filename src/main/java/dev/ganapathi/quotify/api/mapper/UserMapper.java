package dev.ganapathi.quotify.api.mapper;

import dev.ganapathi.quotify.api.dto.user.UserRegister;
import dev.ganapathi.quotify.api.dto.user.UserResponse;
import dev.ganapathi.quotify.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserResponse toResponse(User user, String token){
        return UserResponse.builder().email(user.getEmail())
                .userId(user.getUserId())
                .name(user.getName())
                .token(token)
                .build();
    }

    public User toEntity(UserRegister register){
        return modelMapper.map(register, User.class);
    }
}
