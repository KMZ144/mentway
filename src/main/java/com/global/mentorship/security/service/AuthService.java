package com.global.mentorship.security.service;

import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.global.mentorship.payment.service.PaymentMethodService;
import com.global.mentorship.security.dto.AuthResposne;
import com.global.mentorship.security.dto.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	
	
	private final AuthenticationManager authManager;
		
	private final UserDetailsServiceImpl userDetailsService;
	
	private final JWTUtil jwtUtil;
	
	public AuthResposne authenticate(String email, String password) {
	 authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			
			final UserDetailsImpl userDetails =  userDetailsService.loadUserByUsername(email); 
			final String jwt = jwtUtil.generateToken(userDetails);
		

			
			return new AuthResposne(jwt,userDetails.getName(),
					userDetails.getEmail(),userDetails.getImgUrl()
					, userDetails.getId(), userDetails.getAuthorities()
					.stream().map(authz ->authz.getAuthority()).collect(Collectors.toList()),
					userDetails.isHasPayment());
	}

}
