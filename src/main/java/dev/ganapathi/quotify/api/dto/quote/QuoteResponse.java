package dev.ganapathi.quotify.api.dto.quote;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuoteResponse {
    Long quoteId;
    String quote;
}
