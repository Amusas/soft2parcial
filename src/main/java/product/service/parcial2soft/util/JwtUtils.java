package product.service.parcial2soft.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import product.service.parcial2soft.entity.Cliente;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

/**
 * Utilidad para la generación y validación de tokens JWT con Secret Key (HMAC).
 */
@Component
@Slf4j
public class JwtUtils {

    private static final long EXPIRATION_TIME = 3600000; // 1 hora
    private static final String SECRET = "jG6vFzP9hT8kQ3rN2cB7mE1sL4dW0aZ5yC8uR9oH2gT3pV6qJ0xS1bN8vF4lK7"; // 64+ chars recomendado

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Cliente user) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(EXPIRATION_TIME);

        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .subject(user.getEmail())
                .id(String.valueOf(user.getId()))
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .claim("userId", user.getId())
                .claim("nombre", user.getNombre() + " " + user.getApellido())
                .claim("iss", "ingesis.uniquindio.edu.co")
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

