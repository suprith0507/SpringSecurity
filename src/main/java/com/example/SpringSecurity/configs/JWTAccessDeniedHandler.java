package com.example.SpringSecurity.configs;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JWTAccessDeniedHandler
        implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException ex)
            throws IOException, java.io.IOException {

        response.setStatus(403);

        response.setContentType(
                "application/json");

        response.getWriter().write("""
            {
              "status":403,
              "message":"Access Denied"
            }
            """);
    }
}

