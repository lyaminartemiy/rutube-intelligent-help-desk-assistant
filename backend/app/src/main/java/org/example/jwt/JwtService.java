package org.example.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secretKey}")
    private String secretKey;

    @Value("${security.jwt.expirationTime}")
    private long jwtExpiration;

    public String extractUsernameFromJwt(String token) {
        return extractClaimFromJwt(token, Claims::getSubject);
    }

    public <T> T extractClaimFromJwt(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaimsFromJwt(token);
        return claimsResolver.apply(claims);
    }

    public String generateJwt(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isJwtValid(String token, UserDetails userDetails) {
        final String username = extractUsernameFromJwt(token);
        return (username.equals(userDetails.getUsername())) && !isJwtExpired(token);
    }

    private boolean isJwtExpired(String token) {
        return extractExpirationFromJwt(token).before(new Date());
    }

    private Date extractExpirationFromJwt(String token) {
        return extractClaimFromJwt(token, Claims::getExpiration);
    }

    private Claims extractAllClaimsFromJwt(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
