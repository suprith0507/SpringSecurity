package com.example.SpringSecurity.configs;

import com.example.SpringSecurity.entities.CustomerEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.secret.key}")
    private String secretkey;

    public String generateJWTToken(String username){



        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+60000))
                .signWith(getSecretKey( ))
                .compact();
    }

    private SecretKey getSecretKey( ){
        byte[] keyBytes =
                Decoders.BASE64.decode(secretkey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserNamefromToken(String token)
    {
        return Jwts.parser()
                .verifyWith(getSecretKey( ))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Boolean isTokenExpired(String token)
    {
        Date currentDate =new Date();
        return    currentDate.after (Jwts.parser()
                .verifyWith(getSecretKey( ))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration());
    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String username =
                extractUserNamefromToken(token);

        return username.equals(
                userDetails.getUsername()
        )
                &&
                !isTokenExpired(token);
    }
}
