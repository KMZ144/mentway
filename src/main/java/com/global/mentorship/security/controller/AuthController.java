package com.global.mentorship.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.mentorship.security.dto.AuthRequest;
import com.global.mentorship.security.dto.AuthResposne;
import com.global.mentorship.security.service.AuthService;
import com.global.mentorship.security.service.JWTUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/login")
	public  ResponseEntity<AuthResposne> login (@RequestBody AuthRequest authRequest) {
		return ResponseEntity.ok(authService.authenticate(authRequest.getEmail()
				,authRequest.getPassword()));
	}
	
	
	@PostMapping("/register")
	public  String register () {
		return "register";
	}

}
