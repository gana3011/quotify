package dev.ganapathi.quotify.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_quotes")
public class UserQuotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @ManyToOne
    //delete user_quotes if user is deleted in db
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_user_quotes_user")
    )
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "quote_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_quotes_quote")
    )
    private Quote quote;
}
