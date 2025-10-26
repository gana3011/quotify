package dev.ganapathi.quotify.api.security;


import dev.ganapathi.quotify.domain.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final UserRepository userRepository;

    private final long expirationMillis = 1000L * 60 * 60 * 24 * 7;

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void initKey(){
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    private Key getKey() {
        return key;
    }


    public String generateToken(String subject){
        Map<String, Object> claims = new HashMap<>();
        return buildToken(claims, subject);
    }

    public String generateToken(Map<String, Object> claims, String subject){
        return buildToken(claims, subject);
    }

    private String buildToken(Map<String, Object> claims, String subject){
        var nowMillis = System.currentTimeMillis();
        return Jwts
                .builder()
                .claims(claims)
                .subject(subject)
                .issuer("gana-quotify")
                .issuedAt(new Date(nowMillis))
                .expiration(new Date(nowMillis + expirationMillis))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith((SecretKey) getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("Token invalid");
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, String subject){
        var user = userRepository.findByEmail(subject);
        if(user.isEmpty()) return false;

        final String email = extractEmail(token);
        return email.equals(subject) && !isTokenExpired(token);
    }

}
