package com.global.mentorship.user.repo;

import org.springframework.stereotype.Repository;

import com.global.mentorship.base.repo.BaseRepo;
import com.global.mentorship.user.entity.Roles;

@Repository
public interface RolesRepo extends BaseRepo<Roles, Long> {

	Roles findRoleByName(String name);
}
