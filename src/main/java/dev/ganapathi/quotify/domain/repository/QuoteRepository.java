package dev.ganapathi.quotify.domain.repository;

import dev.ganapathi.quotify.domain.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {
    Optional<Quote> findByQuote(String quote);
    void deleteById(Long id);
}
