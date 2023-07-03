package com.global.mentorship.user.service;

import org.springframework.stereotype.Service;

import com.global.mentorship.base.service.BaseService;
import com.global.mentorship.user.entity.Mentee;
import com.global.mentorship.user.repo.MenteeRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenteeService extends BaseService<Mentee, Long> {
	
	private final MenteeRepo menteeRepo;

	public Mentee findByEmail(String email) {
		// TODO Auto-generated method stub
		return menteeRepo.findByEmail(email);
	}

}
