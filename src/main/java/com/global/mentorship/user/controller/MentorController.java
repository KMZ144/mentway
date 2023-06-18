package com.global.mentorship.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/mentor")
public class MentorController {
	
	@GetMapping("")
	public String findAllMentors() {
		return "allah akbr";
		
	}

	
}
