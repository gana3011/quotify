package dev.ganapathi.quotify.api.mapper;

import dev.ganapathi.quotify.api.dto.user.UserRegister;
import dev.ganapathi.quotify.api.dto.user.UserResponse;
import dev.ganapathi.quotify.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserResponse toResponse(User user){
        return UserResponse.builder().email(user.getEmail())
                .userId(user.getId())
                .name(user.getName())
                .build();
    }
    public User toEntity(UserRegister register){
        return modelMapper.map(register, User.class);
    }
}
