package dev.ganapathi.quotify.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegister {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String password;
}
