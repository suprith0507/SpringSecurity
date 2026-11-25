package com.example.SpringSecurity.configs;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {

    @PostConstruct
    public void generate() {
        System.out.println(
                new BCryptPasswordEncoder()
                        .encode("Welcome@123")
                //$2a$10$pt.JmcKsfFNRTyqrIXE7Vu9uK1eADTx/mSOe8PHzaJazokBaGGQC2
        );
    }
}