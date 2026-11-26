package com.example.SpringSecurity.configs;





import com.example.SpringSecurity.services.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
    @EnableWebSecurity
@RequiredArgsConstructor
    public class SessionCSRFDemo {
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final JWTAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAccessDeniedHandler accessDeniedHandler;
        @Bean
        public SecurityFilterChain mySecurityFilterChain1(
                HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {

            return http
                    .csrf(csrf -> csrf.disable()) //disable csrf for jwt as it is not required
                    //.cors(Customizer.withDefaults())
                    //.csrf(csrff ->csrff.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                    .authorizeHttpRequests(auth ->
                            auth.requestMatchers("/auth/login").permitAll().anyRequest().
                                    authenticated()).
                    authenticationProvider(authenticationProvider)
                    //.httpBasic(Customizer.withDefaults()) //use for browser and postman
                    //.formLogin(Customizer.withDefaults()) //use for browser
                    //.sessionManagement(Customizer.withDefaults())
                    .sessionManagement(session ->
                            session.sessionCreationPolicy(
                                    SessionCreationPolicy
                                            .STATELESS
                            ))//make it stateless for jwt based authentication
                    .addFilterAfter(
                            new CsrfCookieFilter(),
                            CsrfFilter.class)
                    .addFilterBefore(
                            jwtAuthenticationFilter,
                            UsernamePasswordAuthenticationFilter.class)
                    /*.exceptionHandling(ex -> ex
                            .accessDeniedHandler(accessDeniedHandler)
                            .authenticationEntryPoint(authenticationEntryPoint))*/
                    .build();
        }

       //password encoder and other stuff are picked up from the other config file
        //trying to explain JsessionID and CSRF token concept for stateful authentication.
        //exceptions happing in filters and security layer can be passed to globalexception handler in controller using HandlerExceptionResolver.
     //HandlerExceptionResolver acts as a postman and sends the exceptions from security layer to controller.
    //Exceptions in security layer can be handled direclty by using AuthenticationEntryPoint and AccessDeniedHandler directly in filter without passing them to controller.

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config =
                new CorsConfiguration();

        config.setAllowedOrigins(
                List.of(
                        "http://localhost:4200"
                ));

        config.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE"
                ));

        config.setAllowedHeaders(
                List.of("*")
        );

        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                config
        );

        return source;
    }
}
