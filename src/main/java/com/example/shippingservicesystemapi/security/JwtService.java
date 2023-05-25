package com.example.shippingservicesystemapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    @Value(value = "${constants.secret-key}")
    private String SECRET_KEY;

    @Value(value = "#{new Long('${constants.interim}')}")
    private Long INTERIM;

    public String generateJwt(final Authentication authentication) {
        final String username = authentication.getName();
        final Date currentDate = new Date(System.currentTimeMillis());
        final Date expirationDate = new Date(currentDate.getTime()+INTERIM);

        return Jwts.builder()
                .setHeaderParam("alg", "HS512")
                .setHeaderParam("type", "JWT")
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, getSigningKey())
                .compact();
    }

    public String getUsernameFromJwt(final String token) {
        final Claims claims=Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
