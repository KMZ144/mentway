package com.global.mentorship.videocall.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.global.mentorship.base.repo.BaseRepo;
import com.global.mentorship.videocall.dto.MenteeReviewDto;
import com.global.mentorship.videocall.entity.Services;

@Repository
public interface ServicesRepo extends BaseRepo<Services, Long> {

	@Query("SELECT NEW com.global.mentorship.videocall.dto.MenteeReviewDto (" +
	           "m.id, m.name, m.email, m.imgUrl,ms.rate,s.details,s.endDate) " +
	           "FROM MenteesServices ms " +
	           "JOIN ms.services s " +
	           "JOIN ms.mentee m "+
	           "JOIN s.mentor me " +
	           "WHERE me.isValid = true AND me.id = :id "
	           )
	Page<MenteeReviewDto> findAllReviewsByMentorId(long id , Pageable pageable);

}
