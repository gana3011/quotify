package dev.ganapathi.quotify.api.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long userId;
    private String name;
    private String email;
    private String token;
}
