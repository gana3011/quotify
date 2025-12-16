package dev.ganapathi.quotify.api.controller;

import dev.ganapathi.quotify.api.dto.quote.QuoteRegister;
import dev.ganapathi.quotify.api.dto.quote.QuoteResponse;
import dev.ganapathi.quotify.api.mapper.QuoteMapper;
import dev.ganapathi.quotify.api.security.UserPrincipal;
import dev.ganapathi.quotify.domain.model.Quote;
import dev.ganapathi.quotify.domain.service.QuoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class QuoteController {

    private final QuoteService quoteService;
    private final QuoteMapper quoteMapper;

    @PostMapping("users/me/quotes")
    @ResponseStatus(HttpStatus.CREATED)
    public QuoteResponse registerQuote(@AuthenticationPrincipal UserPrincipal user, @Valid @RequestBody QuoteRegister register){
        Long userId = user.getId();
        Quote quote = quoteMapper.toEntity(register);
        return quoteService.createQuote(quote, userId);
    }

    @GetMapping("users/me/quotes")
    public List<QuoteResponse> getUserQuotes(@AuthenticationPrincipal UserPrincipal user){
        Long userId = user.getId();
        return quoteService.getUserQuotes(userId);
    }

    @GetMapping("/quotes")
    public List<QuoteResponse> getAllQuotes(){
        return quoteService.getQuotes();
    }

    @DeleteMapping("/quotes/{id}")
    public void deleteQuote(@PathVariable Long id){
        quoteService.deleteQuote(id);
    }
}
