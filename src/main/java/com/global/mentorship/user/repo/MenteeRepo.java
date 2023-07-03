package com.global.mentorship.user.repo;

import com.global.mentorship.base.repo.BaseRepo;
import com.global.mentorship.user.entity.Mentee;

public interface MenteeRepo extends BaseRepo<Mentee, Long> {

	Mentee findByEmail(String email);

}
