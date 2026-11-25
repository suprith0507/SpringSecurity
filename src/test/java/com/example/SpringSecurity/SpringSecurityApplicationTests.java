package com.example.SpringSecurity;

import com.example.SpringSecurity.configs.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class SpringSecurityApplicationTests {

	@Autowired
	JWTService jwtService;
	@Test
	void contextLoads() {
	}

	@Test
	void generateJWTToken(){


		String token=jwtService.generateJWTToken("suprith.buddharthy");
		System.out.println(token);
		System.out.println(jwtService.extractUserNamefromToken(token));
		System.out.println(jwtService.isTokenExpired(token));
		UserDetails user= User.builder().username("suprith.buddharthy").build();
		System.out.println(jwtService.isTokenValid(token,user));
	}

}
