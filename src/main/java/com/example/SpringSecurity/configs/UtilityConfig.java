package com.example.SpringSecurity.configs;




import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
    @EnableJpaAuditing(
            auditorAwareRef = "auditAwareImpl")
    public class UtilityConfig {

        @Bean
        public ModelMapper modelMapper() {

            return new ModelMapper();
        }
    }

