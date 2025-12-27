package dev.ganapathi.quotify.api.controller;

import dev.ganapathi.quotify.api.dto.Cursor.CursorPage;
import dev.ganapathi.quotify.api.dto.quote.QuoteRegister;
import dev.ganapathi.quotify.api.dto.quote.QuoteResponse;
import dev.ganapathi.quotify.api.mapper.QuoteMapper;
import dev.ganapathi.quotify.api.security.UserPrincipal;
import dev.ganapathi.quotify.domain.model.Quote;
import dev.ganapathi.quotify.domain.service.QuoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


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
    public Page<QuoteResponse> getUserQuotes(@AuthenticationPrincipal UserPrincipal user, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        Long userId = user.getId();
        Pageable pageable = PageRequest.of(page, size);
        return quoteService.getUserQuotes(userId, pageable);
    }

    @GetMapping("/quotes")
    public CursorPage<QuoteResponse> getAllQuotes(@RequestParam(required = false) String cursor, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(0, size + 1);
        return quoteService.getQuotes(cursor, pageable);
    }

    @DeleteMapping("/quotes/{id}")
    public void deleteQuote(@PathVariable Long id){
        quoteService.deleteQuote(id);
    }
}
