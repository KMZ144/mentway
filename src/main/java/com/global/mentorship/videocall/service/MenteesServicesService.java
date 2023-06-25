package com.global.mentorship.videocall.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

import com.global.mentorship.error.NotAvaliableTimeException;
import com.global.mentorship.videocall.dto.MenteeReviewDto;
import com.global.mentorship.videocall.dto.MenteeServicesDto;
import com.global.mentorship.videocall.dto.UpcomingServicesDto;
import com.global.mentorship.videocall.entity.MenteesServices;
import com.global.mentorship.videocall.entity.Services;
import com.global.mentorship.videocall.mapper.MenteeServicesMapper;
import com.global.mentorship.videocall.repo.MenteesServicesRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenteesServicesService {
	
	private final ServicesService servicesService;

	private final MenteesServicesRepo menteesServicesRepo;
	
	private final MenteeServicesMapper menteeServicesMapper;
	
	public Page<MenteeReviewDto> findAllReviewsByMentorId(long id,int page,int size){
		Pageable pageable = PageRequest.of(page, size);
		return menteesServicesRepo.findAllReviewsByMentorId(id,pageable);
	}
	
	public Page<UpcomingServicesDto> findAllUpcomingSessionsByMentorId(long id , int page,int size){
		Pageable pageable = PageRequest.of(page, size);
		return menteesServicesRepo.findAllUpcomingSessionsByMentorId(id, pageable);
	};
	
	public Page<UpcomingServicesDto> findAllUpcomingSessionsByMenteeId(long id , int page,int size){
		Pageable pageable = PageRequest.of(page, size);
		return menteesServicesRepo.findAllUpcomingSessionsByMenteeId(id, pageable);
	};
	
	public MenteeServicesDto requestService (MenteeServicesDto application,long id) {
		if(checkAvaliableTime(application.getStartDate(),id)){
			MenteesServices menteeServices = menteeServicesMapper.unMap(application);
			Services services = servicesService.findById(id);
			menteeServices.setServices(services);
			 System.out.println( menteesServicesRepo.save(menteeServices).getStartDate());
			 
			 return application;
		}
		else throw new NotAvaliableTimeException("not avaliable time please choose another time");
		
	}
	
	private boolean checkAvaliableTime(LocalDateTime dateTime,long id) {
		List<MenteesServices> menteesServices = menteesServicesRepo.findMenteesServicesByServicesId(id);
		int duration  = menteesServices.get(0).getServices().getDuration();
		LocalDateTime dateTimeApplication;
		for (MenteesServices application : menteesServices) {
			 dateTimeApplication = application.getStartDate();
			 System.out.println(dateTimeApplication);
			if((dateTime.isAfter(dateTimeApplication) && dateTime.isBefore(dateTimeApplication.plusHours(duration)))
					|| dateTime.isEqual(dateTimeApplication) ) {
				return false;		
			}
		}
		return true;
	}
}
