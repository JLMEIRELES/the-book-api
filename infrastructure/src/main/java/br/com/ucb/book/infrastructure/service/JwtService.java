package br.com.ucb.book.infrastructure.service;

import br.com.ucb.book.infrastructure.entity.UsuarioEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Getter
@RequiredArgsConstructor
public class JwtService {

    @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
    private String jwtSecret;

    @Value("${spring.security.oauth2.resourceserver.email-jwt.secret-key}")
    private String emailSecret;

    public String generateJwt(UsuarioEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("subject", user.getUsername());
        claims.put("id", user.getId());
        claims.put("scope", user.getAuthorities());

        var decodedKey = Decoders.BASE64.decode(jwtSecret);
        var actualDate = ZonedDateTime.now();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(actualDate.toInstant()))
                .expiration(Date.from(actualDate.plusMinutes(60).toInstant()))
                .signWith(Keys.hmacShaKeyFor(decodedKey))
                .compact();
    }

    public SecretKeySpec getKey() {
        var decodedKey = Decoders.BASE64.decode(jwtSecret);
        return new SecretKeySpec(decodedKey, "HmacSHA256");
    }

    public SecretKeySpec getEmailKey() {
        var decodedKey = Decoders.BASE64.decode(emailSecret);
        return new SecretKeySpec(decodedKey, "HmacSHA256");
    }

    public String generateTokenForEmail(UsuarioEntity usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());

        var decodedKey = Decoders.BASE64.decode(emailSecret);
        var actualDate = ZonedDateTime.now();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(actualDate.toInstant()))
                .expiration(Date.from(actualDate.plusHours(24).toInstant())) // token expira em 24h
                .signWith(Keys.hmacShaKeyFor(decodedKey))
                .compact();
    }
}

