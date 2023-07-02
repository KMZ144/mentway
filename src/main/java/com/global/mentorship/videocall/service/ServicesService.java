package com.global.mentorship.videocall.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.global.mentorship.base.service.BaseService;
import com.global.mentorship.error.NotValidMentorException;
import com.global.mentorship.user.entity.Mentor;
import com.global.mentorship.user.service.MentorService;
import com.global.mentorship.videocall.entity.Services;
import com.global.mentorship.videocall.repo.ServicesRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ServicesService extends BaseService<Services, Long> {

	private final ServicesRepo servicesRepo; 
	
	private final MentorService mentorService;
	
	
	public List<Services> findServicesByMentorId(long id){
		return servicesRepo.findServicesByMentorId(id);
	}
	
//	public Mentor findMentorByServiceId(long id){
//		return servicesRepo.findServicesByMentorId(id);
//	}

	
	public Services insert(Services service,long id) {
		Mentor mentor = mentorService.findById(id);
		if (!mentor.isValid()) {
			throw new NotValidMentorException("this memtor is not valid");
		}
		service.setMentor(mentor);
		return super.insert(service);
	}
	
}
