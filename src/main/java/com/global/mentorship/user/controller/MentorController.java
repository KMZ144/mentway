package com.global.mentorship.user.controller;

import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.mentorship.user.dto.MentorDto;
import com.global.mentorship.user.dto.MentorInfoDto;
import com.global.mentorship.user.service.MentorService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/mentors")
@RequiredArgsConstructor
public class MentorController {
	
	
	private final MentorService mentorService;
		
	@GetMapping("")
	public ResponseEntity<Page<MentorDto>> findAllMentors(
			@RequestParam(name = "rate", required = false) Double rate,
			@RequestParam(name = "categoryId", required = false) Long categoryId,
			@RequestParam(name = "name",required = false) String name,
			@RequestParam(name = "page", defaultValue = "0") int pageNo
			,@RequestParam(name = "size" ,defaultValue = "5") int size){
		return ResponseEntity.ok(mentorService.findAllMentorsWithRating(rate,categoryId,name,pageNo,size));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MentorInfoDto> findMentorById(@PathVariable long id)
			{
		return ResponseEntity.ok(mentorService.findMentorById(id));
	}
	
	
	
}
