package dev.ganapathi.quotify.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogin {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
