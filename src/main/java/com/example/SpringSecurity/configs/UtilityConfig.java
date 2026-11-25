package com.example.SpringSecurity.configs;




import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
    @EnableJpaAuditing(
            auditorAwareRef = "auditAwareImpl")
    public class UtilityConfig {

        @Bean
        public ModelMapper modelMapper() {

            return new ModelMapper();
        }

    }

