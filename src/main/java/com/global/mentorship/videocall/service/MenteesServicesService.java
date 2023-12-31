package com.global.mentorship.videocall.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.global.mentorship.base.service.BaseService;
import com.global.mentorship.error.NotAvaliableTimeException;
import com.global.mentorship.error.NotValidPaymentException;
import com.global.mentorship.notification.service.EmailService;
import com.global.mentorship.payment.entity.Transcations;
import com.global.mentorship.payment.service.PaymentMethodService;
import com.global.mentorship.payment.service.TranscationsService;
import com.global.mentorship.user.entity.Mentee;
import com.global.mentorship.user.entity.Mentor;
import com.global.mentorship.user.service.MenteeService;
import com.global.mentorship.videocall.dto.AdminApplicationsDto;
import com.global.mentorship.videocall.dto.MenteeApplicationsDto;
import com.global.mentorship.videocall.dto.MenteeReviewDto;
import com.global.mentorship.videocall.dto.MenteeServicesDto;
import com.global.mentorship.videocall.dto.UpcomingServicesDto;
import com.global.mentorship.videocall.entity.MenteesServices;
import com.global.mentorship.videocall.entity.Services;
import com.global.mentorship.videocall.entity.Status;
import com.global.mentorship.videocall.mapper.MenteeServicesMapper;
import com.global.mentorship.videocall.repo.MenteesServicesRepo;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Transfer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenteesServicesService extends BaseService<MenteesServices, Long> {

	private final ServicesService servicesService;

	private final MenteeService menteeService;

	private final MenteesServicesRepo menteesServicesRepo;

	private final MenteeServicesMapper menteeServicesMapper;

	private final EmailService mailService;

	private final PaymentMethodService paymentMethodService;

	public Page<MenteeReviewDto> findAllReviewsByMentorId(long id, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return menteesServicesRepo.findAllReviewsByMentorId(id, pageable);
	}
	
	public Page<MenteeServicesDto> findMenteesServicesByMentorId(long id ,int page , int size ){
		Pageable pageable = PageRequest.of(page, size);
		 return menteesServicesRepo.findMenteesServicesByMentorId(id, pageable ,Arrays.asList(Status.PENDINNG));
		 
	}
	
	public Page<MenteeApplicationsDto> findMenteesServicesByMenteeId(long id ,int page , int size ){
		Pageable pageable = PageRequest.of(page, size);
		 return menteesServicesRepo.findMenteesServicesByMenteeId(id, pageable,Arrays.asList(Status.PENDINNG,Status.REJECTED));
		 
	}

	public Page<UpcomingServicesDto> findAllUpcomingSessionsByMentorId(long id, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		LocalDateTime date = LocalDateTime.now().minusHours(3);
		return menteesServicesRepo.findAllUpcomingSessionsByMentorId(id, pageable,date);
	};

	public Page<UpcomingServicesDto> findAllUpcomingSessionsByMenteeId(long id, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		LocalDateTime date = LocalDateTime.now().minusHours(3);  
		return menteesServicesRepo.findAllUpcomingSessionsByMenteeId(id, pageable, date);
	};

	public MenteeServicesDto requestService(MenteeServicesDto application, long serviceId, long menteeId) {
		if (!menteeService.findById(menteeId).isHasValidPayment()) {
			throw new NotValidPaymentException("not valid payment method added");
		}

		if (checkAvaliableServiceTime(application.getStartDate(), serviceId, menteeId)) {
			MenteesServices menteeServices = menteeServicesMapper.unMap(application);
			Services services = servicesService.findById(serviceId);
			Mentee mentee = menteeService.findById(menteeId);
			menteeServices.setServices(services);
			menteeServices.setMentee(mentee);
			menteeServices.setStatus(Status.PENDINNG);
			menteesServicesRepo.save(menteeServices);
			Mentor mentor = services.getMentor();
			mailService.sendEmail(mentor, "Application for Service " + services.getTitle(),
					mentee.getName() + " request for service <br>" + application.getApplicationDetails());
			return application;
		} else
			throw new NotAvaliableTimeException("not avaliable time please choose another time");

	}
	
	public MenteeServicesDto changeApplicationStatus(long appId,String status) throws StripeException {
		MenteesServices app = menteesServicesRepo.findById(appId)
				.orElseThrow(() ->  new NoSuchElementException("no application was found"));
		Mentee mentee = app.getMentee();
		long amount = app.getServices().getPrice();
		if (status.equals("accepted")) {
			return menteeServicesMapper.map (acceptApplication(app));
		}
		return menteeServicesMapper.map (rejectApplication(app));
	}
	
	private MenteesServices rejectApplication(MenteesServices app) {
		Mentee mentee = app.getMentee();
		mailService.sendEmail(mentee,"Your application Has been rejected ",
				"your application for "+app.getServices().getTitle()+ "has been rejected ");
		app.setStatus(Status.REJECTED);
		update(app);
		return app;
	}

	private MenteesServices acceptApplication(MenteesServices app) throws StripeException {
		Mentee mentee = app.getMentee();
		long amount = app.getServices().getPrice();
		app.setStatus(Status.ACCEPTED);
		app.setMeetingUrl(UUID.randomUUID().toString());
		paymentMethodService.payForService(mentee.getStripeId(), amount);
		mailService.sendEmail(mentee,"Your application Has been accepted ",
				"your application for "+app.getServices().getTitle()+ "has been accepted check you dashboard for meeting url");
		return update(app) ;
	}
	
	private boolean checkAvaliableServiceTime(LocalDateTime dateTime, long serviceId, long menteeId) {
		List<MenteesServices> menteesServices = menteesServicesRepo.findMenteesServicesByServicesIdOrMenteeId(serviceId,
				menteeId);
		LocalDateTime dateTimeApplication;
		for (MenteesServices application : menteesServices) {
			dateTimeApplication = application.getStartDate();
			int duration = application.getServices().getDuration();
			if ((dateTime.isAfter(dateTimeApplication) && dateTime.isBefore(dateTimeApplication.plusHours(duration)))
					|| dateTime.isEqual(dateTimeApplication)
					|| (dateTime.isAfter(dateTimeApplication.minusHours(duration))
							&& dateTime.isBefore(dateTimeApplication))) {
				return false;
			}
		}
		return true;
	}

	public void transferFundsToMentor(long id) throws StripeException {
			MenteesServices menteesServices = findById(id);
			paymentMethodService.transferFundsToMentor(menteesServices);
			menteesServices.setPaidDone(true);
			update(menteesServices);
		}
	
	public Page<AdminApplicationsDto> getAllUnpaidApplications(int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		LocalDateTime date = LocalDateTime.now().minusDays(2);
		return menteesServicesRepo.getAllUnpaidApplications(pageable,date); 
	}
	
	
}

