package com.example.SpringSecurity.configs;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException ex)
            throws IOException, java.io.IOException {

        response.setStatus(401);

        response.setContentType(
                "application/json");

        response.getWriter().write("""
            {
               "status":401,
               "message":"Authentication Failed"
            }
            """);
    }
}

