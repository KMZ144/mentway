package com.global.mentorship.videocall.repo;

import org.springframework.stereotype.Repository;

import com.global.mentorship.base.repo.BaseRepo;
import com.global.mentorship.videocall.entity.Services;
import java.util.List;

@Repository
public interface ServicesRepo extends BaseRepo<Services, Long> {


	List<Services> findServicesByMentorId(long id);
	
	
}
