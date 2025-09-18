package com.ecommerce.app.infrastructure.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {
    private final String jwtSecret = "nj8f+OwZtegrWTovLoUUcfq9BbaC9T9QyB7dIiqjybpRTMqizrdGDPmel2CYdgpV54CGlyyjqpglJI4E0i5knw==";
    private final Key key = Keys.hmacShaKeyFor((Base64.getDecoder().decode(jwtSecret)));

    public JwtUtil() {
        Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public String generateToken(String nombreUsuario) {
        int jwtExpirationMs = 86400000;
        return Jwts.builder()
                .setSubject(nombreUsuario)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
            return false;
        }
    }
}
