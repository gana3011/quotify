package dev.ganapathi.quotify.api.controller;

import dev.ganapathi.quotify.api.dto.quote.QuoteRegister;
import dev.ganapathi.quotify.api.dto.quote.QuoteResponse;
import dev.ganapathi.quotify.api.mapper.QuoteMapper;
import dev.ganapathi.quotify.api.security.UserPrincipal;
import dev.ganapathi.quotify.domain.model.Quote;
import dev.ganapathi.quotify.domain.service.QuoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class QuoteController {

    private final QuoteService quoteService;
    private final QuoteMapper quoteMapper;

    @PostMapping("users/me/quotes")
    public ResponseEntity<QuoteResponse> registerQuote(@AuthenticationPrincipal UserPrincipal user, @Valid @RequestBody QuoteRegister register){
        Long userId = user.getId();
        Quote quote = quoteMapper.toEntity(register);
        return ResponseEntity.ok(quoteService.createQuote(quote, userId));
    }
}
