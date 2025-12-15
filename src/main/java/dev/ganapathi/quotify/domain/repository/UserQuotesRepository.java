package dev.ganapathi.quotify.domain.repository;

import dev.ganapathi.quotify.domain.model.UserQuotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuotesRepository extends JpaRepository<UserQuotes, Long> {
}
