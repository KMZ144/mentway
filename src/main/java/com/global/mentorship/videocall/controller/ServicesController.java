package com.global.mentorship.videocall.controller;

import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.mentorship.security.dto.UserDetailsImpl;
import com.global.mentorship.user.service.MentorService;
import com.global.mentorship.videocall.dto.MenteeReviewDto;
import com.global.mentorship.videocall.dto.MenteeServicesDto;
import com.global.mentorship.videocall.dto.ServicesDto;
import com.global.mentorship.videocall.dto.UpcomingServicesDto;
import com.global.mentorship.videocall.entity.Services;
import com.global.mentorship.videocall.mapper.ServicesMapper;
import com.global.mentorship.videocall.service.MenteesServicesService;
import com.global.mentorship.videocall.service.ServicesService;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("api/v1/services")
@RequiredArgsConstructor
public class ServicesController {
	
 private final ServicesService servicesService;
 
 private final MenteesServicesService menteesServicesService;
 
 private final ServicesMapper servicesMapper;
 
 
 	@GetMapping("/{id}")
 	public ResponseEntity<ServicesDto> findServiceById(@PathVariable long id){
 		return ResponseEntity.ok(servicesMapper.map(servicesService.findById(id)));
 	}

 
 
 	@GetMapping("/mentor/{id}")
 	public ResponseEntity<List<ServicesDto>> findServiceByMentorId(@PathVariable long id){
 		return ResponseEntity.ok(servicesMapper.map(servicesService.findServicesByMentorId(id)));
 	}

	@GetMapping("/review/mentors/{id}")
	public ResponseEntity<Page<MenteeReviewDto>> findAllReviewsByMentorId(
			@PathVariable long id,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size){
		return ResponseEntity.ok(menteesServicesService.findAllReviewsByMentorId(id,page,size));
	}
	
	@GetMapping("/upcoming/mentors")
	public ResponseEntity<Page<UpcomingServicesDto>> findAllUpcomingSessionsByMentorId(
			@PathVariable long id,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			Authentication auth
			){
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		return ResponseEntity.ok(menteesServicesService.findAllUpcomingSessionsByMentorId(user.getId(),page,size));
	}
	
	@GetMapping("/upcoming/mentee")
	public ResponseEntity<Page<UpcomingServicesDto>> findAllUpcomingSessionsByMenteeId(
			@PathVariable long id,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			Authentication auth
			){
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		return ResponseEntity.ok(menteesServicesService.findAllUpcomingSessionsByMenteeId(user.getId(),page,size));
	}


	
	@PostMapping("")
	public ResponseEntity<ServicesDto> addService(@RequestBody ServicesDto servicesDto,Authentication auth){
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		Services servicesCreated = servicesService.insert(servicesMapper.unMap(servicesDto),user.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(servicesMapper.map(servicesCreated));
	}
	
	@PostMapping("apply/{id}")
	public ResponseEntity<MenteeServicesDto> requestService(@RequestBody MenteeServicesDto servicesDto
			,@PathVariable long id,Authentication auth){
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		System.out.println(user);
		MenteeServicesDto application = menteesServicesService.requestService(servicesDto, id,user.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(application);
	}
	
	
	
}
