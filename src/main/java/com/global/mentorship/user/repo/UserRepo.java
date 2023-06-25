package com.global.mentorship.user.repo;

import com.global.mentorship.base.repo.BaseRepo;
import com.global.mentorship.user.entity.User;

public interface UserRepo extends BaseRepo<User, Long> {

	User findByEmail(String email);
}
