package dev.ganapathi.quotify.domain.service;

import dev.ganapathi.quotify.api.dto.Cursor.CursorPage;
import dev.ganapathi.quotify.api.dto.quote.QuoteResponse;
import dev.ganapathi.quotify.api.mapper.QuoteMapper;
import dev.ganapathi.quotify.domain.model.Quote;
import dev.ganapathi.quotify.domain.model.User;
import dev.ganapathi.quotify.domain.model.UserQuotes;
import dev.ganapathi.quotify.domain.repository.QuoteRepository;
import dev.ganapathi.quotify.domain.repository.UserQuotesRepository;
import dev.ganapathi.quotify.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;
    private final UserQuotesRepository userQuotesRepository;
    private final CursorService cursorService;
    private final QuoteMapper quoteMapper;

    @Transactional
    public QuoteResponse createQuote(Quote quote, Long userId) {
        checkQuoteAvailable(quote);
        Quote savedQuote = quoteRepository.save(quote);
        User user = userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User doesnt exists"));
        UserQuotes quotesEntity = new UserQuotes();
        quotesEntity.setQuote(quote);
        quotesEntity.setUser(user);
        userQuotesRepository.save(quotesEntity);
        return quoteMapper.toResponse(savedQuote);
    }

    public Page<QuoteResponse> getUserQuotes(Long userId, Pageable pageable) {
        Page<Quote> quotes = userQuotesRepository.findQuotesByUserId(userId, pageable);
        return quotes.map(quoteMapper :: toResponse);
    }

    public CursorPage<QuoteResponse> getQuotes(String cursor, Pageable pageable) {
        Long decoded = cursor == null ? null : cursorService.decode(cursor);
        List<Quote> quotes = quoteRepository.findAll(
                decoded == null ? null : decoded,
                pageable
        );

        String nextCursor = null;

        boolean hasNext = quotes.size() > pageable.getPageSize() - 1;

        if(hasNext){
            Quote last = quotes.getLast();
            nextCursor = cursorService.encode(last.getId());
        }

        List<QuoteResponse> responses = quotes.stream().map(quoteMapper :: toResponse).toList();

        return new CursorPage<>(responses, nextCursor);
    }

    @Transactional
    public void deleteQuote(Long id){
        quoteRepository.deleteById(id);
    }

    private void checkQuoteAvailable(Quote quote) {
        var existingQuote = quoteRepository.findByQuote(quote.getQuote());
        if (existingQuote.isPresent() && existingQuote.get().equals(quote)) {
            throw new RuntimeException("Quote already exists");
        }
    }



}
