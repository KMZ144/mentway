package com.global.mentorship.videocall.controller;

import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.mentorship.security.dto.UserDetailsImpl;
import com.global.mentorship.user.service.MentorService;
import com.global.mentorship.videocall.dto.AdminApplicationsDto;
import com.global.mentorship.videocall.dto.MenteeApplicationsDto;
import com.global.mentorship.videocall.dto.MenteeReviewDto;
import com.global.mentorship.videocall.dto.MenteeServicesDto;
import com.global.mentorship.videocall.dto.ServicesDto;
import com.global.mentorship.videocall.dto.UpcomingServicesDto;
import com.global.mentorship.videocall.entity.Services;
import com.global.mentorship.videocall.mapper.ServicesMapper;
import com.global.mentorship.videocall.service.MenteesServicesService;
import com.global.mentorship.videocall.service.ServicesService;
import com.stripe.exception.StripeException;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

 
 	@GetMapping("applications/mentor")
 	public ResponseEntity<Page<MenteeServicesDto>> findMenteesServicesByMentorId(
 			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			Authentication auth){
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
 		return ResponseEntity.ok(menteesServicesService.findMenteesServicesByMentorId(user.getId(),page,size));
 	}
 	
 	@GetMapping("applications/mentee")
 	public ResponseEntity<Page<MenteeApplicationsDto>> findMenteesServicesByMenteeId(
 			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			Authentication auth){
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
 		return ResponseEntity.ok(menteesServicesService.findMenteesServicesByMenteeId(user.getId(),page,size));
 	}
 	
 
 	@GetMapping("/mentor/{id}")
 	public ResponseEntity<List<ServicesDto>> findServiceByMentorId(@PathVariable long id){

 		return ResponseEntity.ok(servicesMapper.map(servicesService.findServicesByMentorId(id)));
 	}

	@GetMapping("/review/mentors/{id}")
	public ResponseEntity<Page<MenteeReviewDto>> findAllReviewsByMentorId(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			Authentication auth){
 		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		return ResponseEntity.ok(menteesServicesService.findAllReviewsByMentorId(user.getId(),page,size));
	}
	
	@GetMapping("/upcoming/mentor")
	public ResponseEntity<Page<UpcomingServicesDto>> findAllUpcomingSessionsByMentorId(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			Authentication auth
			){
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		return ResponseEntity.ok(menteesServicesService.findAllUpcomingSessionsByMentorId(user.getId(),page,size));
	}
	
	@GetMapping("/upcoming/mentee")
	public ResponseEntity<Page<UpcomingServicesDto>> findAllUpcomingSessionsByMenteeId(
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
		MenteeServicesDto application = menteesServicesService.requestService(servicesDto, id,user.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(application);
	}
	
	@PatchMapping("/application/{id}")
	public ResponseEntity<MenteeServicesDto> changeApplicationStatus(@RequestParam String status
			,@PathVariable long id,
			Authentication auth) throws StripeException{
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		MenteeServicesDto application = menteesServicesService.changeApplicationStatus(id,status);
		return ResponseEntity.status(HttpStatus.CREATED).body(application);
	}
	
	@GetMapping("pay/application/{id}")
	public ResponseEntity<?> payMentorForSession(@PathVariable long id) throws StripeException{
		menteesServicesService.transferFundsToMentor(id);
		Map<String,Boolean> map = new HashMap<>();
		map.put("success", true);
		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/applications/unpaid")
	public ResponseEntity<Page<AdminApplicationsDto>> getUnpaidApplications(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size
			){
		return ResponseEntity.ok (menteesServicesService.getAllUnpaidApplications(page, size));
		
	}
		
	
}
