package dev.ganapathi.quotify.domain.repository;

import dev.ganapathi.quotify.domain.model.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {
    Optional<Quote> findByQuote(String quote);
    void deleteById(Long id);

    @Query("""
    select q from Quote q where (:id is null or q.id < :id) order by q.id desc 
""")
    List<Quote> findAll(@Param("id") Long id, Pageable pageable);
}
