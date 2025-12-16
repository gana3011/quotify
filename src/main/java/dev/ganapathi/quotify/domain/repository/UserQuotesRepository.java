package dev.ganapathi.quotify.domain.repository;

import dev.ganapathi.quotify.domain.model.Quote;
import dev.ganapathi.quotify.domain.model.UserQuotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserQuotesRepository extends JpaRepository<UserQuotes, Long> {
    @Query("""
    SELECT uq.quote from UserQuotes uq where uq.user.userId = :userId
    """)
    List<Quote> findQuotesByUserId(@Param("userId") Long userId);
}
