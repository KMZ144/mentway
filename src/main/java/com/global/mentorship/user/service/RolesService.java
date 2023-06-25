package com.global.mentorship.user.service;

import org.springframework.stereotype.Service;

import com.global.mentorship.base.service.BaseService;
import com.global.mentorship.user.entity.Roles;
import com.global.mentorship.user.repo.RolesRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RolesService extends BaseService<Roles, Long> {

	private final RolesRepo rolesRepo; 
	
	public Roles findByName(String name) {
		return rolesRepo.findRoleByName(name);
	}
}
