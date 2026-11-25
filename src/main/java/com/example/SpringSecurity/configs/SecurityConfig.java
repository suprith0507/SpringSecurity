package com.example.SpringSecurity.configs;

import com.example.SpringSecurity.services.CustomerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain mySecurityFilterChain1(
                HttpSecurity http,AuthenticationProvider authenticationProvider) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().
                                authenticated()).authenticationProvider(authenticationProvider)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


   @Bean
    @Primary
    public UserDetailsService getCustomInMemoryUserDetails()
    {
        UserDetails User1 = User.builder().username("admin").password(passwordEncoder()
                .encode("Welcome@123")).roles("ADMIN").build();
        UserDetails User2 = User.builder().username("bankuser2").password(passwordEncoder()
                .encode("Welcome@123")).roles("ADMIN").build();
        return  new InMemoryUserDetailsManager(User1,User2);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            CustomerServiceImpl customerService,
            UserDetailsService getCustomInMemoryUserDetails,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(customerService);

        DaoAuthenticationProvider provider1 =
                new DaoAuthenticationProvider(getCustomInMemoryUserDetails);

        //OAuth2LoginAuthenticationProvider or any custom authentication provide also.

        provider.setPasswordEncoder(
                passwordEncoder);
        provider1.setPasswordEncoder(
                passwordEncoder);

        return provider1;
    }
    //spring checks for securityfilterchainconfig. if found, it will use, otherwise it will use default inbuilt security filter chain .
    //spring will use securityfilterchain only when we add the dependency in pom.xml
    //creating a bean with custom filterchainconfig is optional. if not found, spring uses default security filer chain config.
    //spring needs UserDetailsService and password encoder beans to decide which authenticaion method to pick.
    //spring picks up DAOAuthenticationProvider if it finds UserDetailsService(inmemory or DAO implemented) and password encoder .

    //Found 2 UserDetailsService beans, with names [customerServiceImpl, getCustomInMemoryUserDetails]. Global Authentication Manager will not use a UserDetailsService for username/password login. Consider publishing a single UserDetailsService bean.
    //you can override the behaviour of choosing default as DAOAuthenticationProvider by implementing your own method to return ur own authenticaiton provider.
    //you can also override inside this authenticaiotnprovider and say which userdetails service(inmemory/doa) to be used if we want to use DAOAuthenticationprovider.
    //you need to register the authenticationprovider in the securityfilterchain if you are explicitly defining the authentication provider. else it wont get linked to the chain.
}