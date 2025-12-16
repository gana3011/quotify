package dev.ganapathi.quotify.domain.service;

import dev.ganapathi.quotify.api.dto.quote.QuoteResponse;
import dev.ganapathi.quotify.api.mapper.QuoteMapper;
import dev.ganapathi.quotify.domain.model.Quote;
import dev.ganapathi.quotify.domain.model.User;
import dev.ganapathi.quotify.domain.model.UserQuotes;
import dev.ganapathi.quotify.domain.repository.QuoteRepository;
import dev.ganapathi.quotify.domain.repository.UserQuotesRepository;
import dev.ganapathi.quotify.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;
    private final UserQuotesRepository userQuotesRepository;
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

    public List<QuoteResponse> getUserQuotes(Long userId) {
        List<Quote> quotes = userQuotesRepository.findQuotesByUserId(userId);
        return quotes.stream().map(quoteMapper :: toResponse).collect(Collectors.toList());
    }

    public List<QuoteResponse> getQuotes() {
        List<Quote> quotes = quoteRepository.findAll();
        return quotes.stream().map(quoteMapper :: toResponse).collect(Collectors.toList());
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
