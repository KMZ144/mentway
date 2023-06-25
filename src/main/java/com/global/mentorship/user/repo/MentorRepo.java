package com.global.mentorship.user.repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.global.mentorship.base.repo.BaseRepo;
import com.global.mentorship.user.dto.MentorDto;
import com.global.mentorship.user.dto.MentorInfoDto;
import com.global.mentorship.user.entity.Mentor;

@Repository
public interface MentorRepo extends BaseRepo<Mentor, Long> {
	

	@Query("SELECT NEW com.global.mentorship.user.dto.MentorDto (" +
	           "AVG(ms.rate), m.id, m.name, m.imgUrl, c.name) " +
	           "FROM MenteesServices ms " +
	           "JOIN ms.services s " +
	           "RIGHT JOIN s.mentor m " +
	           "JOIN m.category c " +
	           "WHERE m.isValid = true " +
	           "AND (:categoryId IS NULL OR c.id =:categoryId) "+
	           "AND (:name IS NULL OR m.name LIKE %:name%) "+
	           "GROUP BY m.id "+
	           "HAVING :rate IS NULL OR AVG(ms.rate) >= :rate ")
	Page<MentorDto> findAllMentorsWithRating(Double rate ,Long categoryId,String name,Pageable pageable);

	
	@Query("SELECT NEW com.global.mentorship.user.dto.MentorInfoDto (" +
	           "m.id, m.name, m.imgUrl, c.name,m.email,m.coverLetter) " +
//	           "JOIN ms.services s " +
	           "FROM Mentor m " +
	           "JOIN m.category c " +
	           "WHERE m.isValid = true AND m.id = :id ")
	MentorInfoDto findMentorById(long id);
	
	}
