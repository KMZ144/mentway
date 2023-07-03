package com.global.mentorship.videocall.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import com.global.mentorship.base.repo.BaseRepo;
import com.global.mentorship.videocall.dto.AdminApplicationsDto;
import com.global.mentorship.videocall.dto.MenteeApplicationsDto;
import com.global.mentorship.videocall.dto.MenteeReviewDto;
import com.global.mentorship.videocall.dto.MenteeServicesDto;
import com.global.mentorship.videocall.dto.UpcomingServicesDto;
import com.global.mentorship.videocall.entity.MenteesServices;
import com.global.mentorship.videocall.entity.Status;

public interface MenteesServicesRepo extends BaseRepo<MenteesServices, Long> {

	@Query("SELECT NEW com.global.mentorship.videocall.dto.MenteeReviewDto (" +
	           "m.id, m.name, m.email, m.imgUrl,ms.rate,s.details) " +
	           "FROM MenteesServices ms " +
	           "JOIN ms.services s " +
	           "JOIN ms.mentee m "+
	           "JOIN s.mentor me " +
	           "WHERE me.isValid = true AND me.id = :id "
	           )
	Page<MenteeReviewDto> findAllReviewsByMentorId(long id , Pageable pageable);


	@Query("SELECT NEW com.global.mentorship.videocall.dto.UpcomingServicesDto (" +
	           "ms.id,s.id,ms.meetingUrl,s.duration,m.id, m.name, m.imgUrl,s.details,ms.startDate) " +
	           "FROM MenteesServices ms " +
	           "JOIN ms.services s " +
	           "JOIN ms.mentee m "+
	           "JOIN s.mentor me " +
	           "WHERE me.isValid = true AND ms.status = ACCEPTED AND (ms.startDate > now() OR ms.startDate > :date) AND me.id = :id "
	           )
	Page<UpcomingServicesDto> findAllUpcomingSessionsByMentorId(long id , Pageable pageable,LocalDateTime date);

	@Query("SELECT NEW com.global.mentorship.videocall.dto.UpcomingServicesDto (" +
	           "ms.id,s.id,ms.meetingUrl,s.duration,me.id ,me.name, me.imgUrl,s.details,ms.startDate) " +
	           "FROM MenteesServices ms " +
	           "JOIN ms.services s " +
	           "JOIN ms.mentee m "+
	           "JOIN s.mentor me " +
	           "WHERE me.isValid = true AND ms.status = ACCEPTED AND (ms.startDate > now() OR ms.startDate > :date)  AND m.id = :id "
	           )
	
	Page<UpcomingServicesDto> findAllUpcomingSessionsByMenteeId(long id , Pageable pageable , LocalDateTime date);

	
	List <MenteesServices> findMenteesServicesByServicesIdOrMenteeId(long serviceId,long menteeId);
	
	MenteesServices findByServicesIdAndMenteeId(long serviceId,long menteeId);


	@Query("SELECT NEW com.global.mentorship.videocall.dto.MenteeServicesDto (" +
	           "ms.id,ms.mentee.id,ms.mentee.name,ms.mentee.imgUrl,ms.services.id,ms.applicationDetails,ms.startDate,ms.status) " +
	           "FROM MenteesServices ms " +
	           "JOIN ms.services s " +
	           "JOIN s.mentor me " +
	           "WHERE me.isValid = true  AND me.id = :id AND ms.status in (:statusList) "
	           )
	Page<MenteeServicesDto> findMenteesServicesByMentorId(long id, Pageable pageable , List<Status> statusList);
	
	
	@Query("SELECT NEW com.global.mentorship.videocall.dto.MenteeApplicationsDto (" +
	           "ms.id,ms.services.title,ms.services.details,ms.services.price ,"
	           + "ms.services.duration,ms.applicationDetails,ms.startDate, "
	           + "ms.services.mentor.id,ms.services.mentor.name,ms.services.mentor.imgUrl,"
	           + "ms.services.mentor.category.name,ms.status )" +
	           "FROM MenteesServices ms " +
	           "WHERE  ms.mentee.id = :id AND (ms.status in :statusList )   "
	           )
	Page<MenteeApplicationsDto> findMenteesServicesByMenteeId(long id, Pageable pageable,List<Status> statusList );


	@Query("SELECT NEW com.global.mentorship.videocall.dto.AdminApplicationsDto (" +
	           "ms.id,ms.services.title,ms.services.details,ms.services.price ,"
	           + "ms.services.duration,ms.applicationDetails,ms.startDate,ms.report, "
	           + "ms.services.mentor.id,ms.services.mentor.name,ms.services.mentor.imgUrl,"
	           + "ms.services.mentor.category.name,ms.status )" +
	           "FROM MenteesServices ms " +
	           "WHERE  ms.paidDone = false AND ms.startDate < :date   "
	           )
	Page<AdminApplicationsDto> getAllUnpaidApplications(Pageable pageable,LocalDateTime date);

}
