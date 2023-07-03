package com.global.mentorship.security.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.mentorship.security.dto.AuthRequest;
import com.global.mentorship.security.dto.AuthResposne;
import com.global.mentorship.security.dto.RegisteredMentee;
import com.global.mentorship.security.dto.RegisteredMentor;
import com.global.mentorship.security.service.AuthService;
import com.global.mentorship.security.service.JWTUtil;
import com.global.mentorship.user.dto.MentorDto;

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
	
	
	@PostMapping( consumes ={ "multipart/form-data" } , path = "/register/mentor")
	public  ResponseEntity<AuthResposne> registerMentor (@ModelAttribute RegisteredMentor mentorDto) throws IOException {
		return ResponseEntity.ok(authService.registerMentor(mentorDto));
	
	}
	
	@PostMapping( consumes ={ "multipart/form-data" } , path = "/register/mentee")
	public  ResponseEntity<AuthResposne> registerMentee (@ModelAttribute RegisteredMentee menteeDto) throws IOException {
		return ResponseEntity.ok(authService.registerMentee(menteeDto));
	
	}

}
