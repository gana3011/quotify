package dev.ganapathi.quotify.api.dto.quote;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QuoteRegister {
    @NotBlank
    String quote;
    @NotBlank
    String author;
}
