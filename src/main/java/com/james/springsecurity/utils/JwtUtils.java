package com.james.springsecurity.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${jwt.expiration}")
    private String expiration;
    @Value("${jwt.secret}")
    private String secret;
    private Key generateKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("Role", userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername());
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public String extractUserName(String token){
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }


    public Object extractUserRole(String token){
        Claims claims = extractAllClaims(token);
        return claims.get("Role");
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(generateKey()).build()
                .parseClaimsJws(token)
                .getBody();
    }
    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(generateKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()));
    }
}


