package com.global.mentorship.user.repo;

import org.springframework.stereotype.Repository;

import com.global.mentorship.base.repo.BaseRepo;
import com.global.mentorship.user.entity.Mentor;

@Repository
public interface MentorRepo extends BaseRepo<Mentor, Long> {

	
}
