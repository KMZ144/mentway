package com.global.mentorship.videocall.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

import com.global.mentorship.error.NoPayemntMethodException;
import com.global.mentorship.error.NotAvaliableTimeException;
import com.global.mentorship.payment.repo.PaymentMethodRepo;
import com.global.mentorship.payment.service.PaymentMethodService;
import com.global.mentorship.user.entity.Mentee;
import com.global.mentorship.user.service.MenteeService;
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
	
	private final MenteeService menteeService;

	private final MenteesServicesRepo menteesServicesRepo;
	
	private final MenteeServicesMapper menteeServicesMapper;
	
	private final PaymentMethodService paymentMethodService;

	
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
	
	public MenteeServicesDto requestService (MenteeServicesDto application
			,long serviceId, long menteeId) {
		paymentMethodService.findPaymentMethodsByUserId(menteeId) ;
		
		
		if(checkAvaliableServiceTime(application.getStartDate(),serviceId,menteeId)){
			MenteesServices menteeServices = menteeServicesMapper.unMap(application);
			Services services = servicesService.findById(serviceId);
			Mentee mentee = menteeService.findById(menteeId);
			menteeServices.setServices(services);
			menteeServices.setMentee(mentee);
			menteesServicesRepo.save(menteeServices);
			 return application;
		}
		else throw new NotAvaliableTimeException("not avaliable time please choose another time");
		
	}
	
	
	
	private boolean checkAvaliableServiceTime(LocalDateTime dateTime,long serviceId,long menteeId) {
		List<MenteesServices> menteesServices = menteesServicesRepo.findMenteesServicesByServicesIdOrMenteeId(serviceId,menteeId);
		LocalDateTime dateTimeApplication;
		for (MenteesServices application : menteesServices) {
			 dateTimeApplication = application.getStartDate();
			 int duration = application.getServices().getDuration();
			if((dateTime.isAfter(dateTimeApplication)
					&& dateTime.isBefore(dateTimeApplication.plusHours(duration)))
					|| dateTime.isEqual(dateTimeApplication)
					|| (dateTime.isAfter(dateTimeApplication.minusHours(duration)) 
							&& dateTime.isBefore(dateTimeApplication))
					) {
				return false;		
			}
		}
		return true;
	}
}
