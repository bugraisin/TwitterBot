package com.twitterbot.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${twitterbot.app.secret}")
    private String APP_SECRET;

    @Value("${twitterbot.expiration.time}")
    private long EXPIRATION_TIME;

    public String generateJwtToken(Authentication auth) {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expirationDate = new Date(new Date().getTime() + EXPIRATION_TIME);
        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date()).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
    }

    Long getUserIdFromJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET).build().parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).build().parseClaimsJws(authToken);
            return !isTokenExpired(authToken);
        } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    boolean isTokenExpired(String authToken) {
        Date expirationDate = Jwts.parser().setSigningKey(APP_SECRET).build().parseClaimsJws(authToken).getBody().getExpiration();
        return expirationDate.before(new Date());
    }

}
