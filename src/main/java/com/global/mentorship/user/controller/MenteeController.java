package com.global.mentorship.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.mentorship.user.dto.MenteeDto;
import com.global.mentorship.user.entity.Mentee;
import com.global.mentorship.user.mapper.MenteeMapper;
import com.global.mentorship.user.service.MenteeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentees")
public class MenteeController {
	
	private final MenteeService menteeService;
	
	private final MenteeMapper menteeMapper;
	
	
	@GetMapping("")
	ResponseEntity<Page<MenteeDto>> findAllMentees (@RequestParam(name = "page", defaultValue = "0") int pageNo,@RequestParam(name = "size" ,defaultValue = "5") int size){
		return ResponseEntity.ok(menteeService.findAll(PageRequest.of(pageNo, size)).map(menteeMapper::map));
	}
	
	@GetMapping("/{id}")
	ResponseEntity<MenteeDto> findMenteeById (@PathVariable long id){
		return ResponseEntity.ok(menteeMapper.map(menteeService.findById(id)));
	}
	

}
