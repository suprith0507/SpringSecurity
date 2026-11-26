package com.example.SpringSecurity.controllers;

import com.example.SpringSecurity.configs.JWTService;
import com.example.SpringSecurity.dtos.LoginDTO;
import com.example.SpringSecurity.dtos.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    @PostMapping(path="/login")
    public ResponseEntity<LoginResponseDTO> validateUserDetails (@RequestBody LoginDTO loginBean)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginBean.getUsername(),loginBean.getPassword()));


        return new ResponseEntity<>( new LoginResponseDTO (jwtService.generateJWTToken(loginBean.getUsername())), HttpStatus.OK);
    }
}
