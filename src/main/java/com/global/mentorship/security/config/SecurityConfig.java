package com.global.mentorship.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig  {

	private final JwtRequestFilter jwtRequestFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager(); 
	}
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.sessionManagement(
				session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.cors(cors -> cors.disable())
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth.requestMatchers("api/v1/auth/**").permitAll())
		.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET,"api/v1/mentors/**").permitAll())
		.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
		.exceptionHandling(auth -> auth.authenticationEntryPoint(
				(req,res,ex)->{
					res.sendError(HttpServletResponse.SC_UNAUTHORIZED,ex.getMessage());
				}));
		
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
		
	}
	
	 @Bean
	    GrantedAuthorityDefaults grantedAuthorityDefaults() {
	        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
	    }
	
	
		
}
