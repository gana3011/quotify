package dev.ganapathi.quotify.api.mapper;

import dev.ganapathi.quotify.api.dto.quote.QuoteRegister;
import dev.ganapathi.quotify.api.dto.quote.QuoteResponse;
import dev.ganapathi.quotify.domain.model.Quote;
import dev.ganapathi.quotify.domain.model.UserQuotes;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuoteMapper {
    private final ModelMapper mapper;

    public QuoteResponse toResponse(UserQuotes quote){
       return QuoteResponse.builder().id(quote.getId()).userId(quote.getUser().getUserId()).quoteId(quote.getQuote().getId()).build();
    }

    public Quote toEntity(QuoteRegister register){
        return mapper.map(register, Quote.class);
    }
}
