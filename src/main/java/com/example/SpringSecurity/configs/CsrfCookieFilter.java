package com.example.SpringSecurity.configs;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class
CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        CsrfToken csrfToken =
                (CsrfToken) request.getAttribute("_csrf");

        if (csrfToken != null) {
            // Forces token generation
            csrfToken.getToken();

            System.out.println("CSRF Token = "
                    + csrfToken.getToken());
        }

        filterChain.doFilter(request, response);
    }
}