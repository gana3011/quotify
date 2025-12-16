package dev.ganapathi.quotify.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quotes", indexes = @Index(columnList = "quote"))
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quote_id")
    Long id;

    @Column(unique = true)
    @EqualsAndHashCode.Include
    String quote;
    String author;

    @OneToMany(mappedBy = "quote")
    private List<UserQuotes> userQuotes;
}
