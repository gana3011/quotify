package dev.ganapathi.quotify.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class CursorService {
    private final ObjectMapper objectMapper =  new ObjectMapper();

    public String encode(Long id){
        try {
            String json = objectMapper.writeValueAsString(id);
            return Base64.getEncoder().encodeToString(json.getBytes());
        } catch (Exception e){
            throw new IllegalArgumentException("Invalid cursor");
        }
    }

    public Long decode(String cursor){
        try {
            byte[] decoded = Base64.getDecoder().decode(cursor);
            return objectMapper.readValue(decoded, Long.class);
        } catch (Exception e){
            throw new IllegalArgumentException("Invalid cursor");
        }
    }

}
