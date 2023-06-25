package com.global.mentorship.videocall.service;

import org.springframework.data.domain.Page;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.mentorship.base.service.BaseService;
import com.global.mentorship.videocall.dto.MenteeReviewDto;
import com.global.mentorship.videocall.dto.MenteeServicesDto;
import com.global.mentorship.videocall.dto.UpcomingServicesDto;
import com.global.mentorship.videocall.entity.Services;
import com.global.mentorship.videocall.repo.ServicesRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ServicesService extends BaseService<Services, Long> {

	private final ServicesRepo servicesRepo; 
	
	public List<Services> findServicesByMentorId(long id){
		return servicesRepo.findServicesByMentorId(id);
	}
	
}
