package com.global.mentorship.videocall.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.mentorship.videocall.dto.MenteeReviewDto;
import com.global.mentorship.videocall.service.ServicesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/services")
@RequiredArgsConstructor
public class ServicesController {
	
 private final ServicesService servicesService;

	@GetMapping("/review/mentors/{id}")
	public ResponseEntity<Page<MenteeReviewDto>> findAllReviewsByMentorId(
			@PathVariable long id,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size){
		return ResponseEntity.ok(servicesService.findAllReviewsByMentorId(id,page,size));
	}
}
