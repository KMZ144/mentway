package com.global.mentorship.user.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.mentorship.base.service.BaseService;
import com.global.mentorship.user.dto.MentorDto;
import com.global.mentorship.user.dto.MentorInfoDto;
import com.global.mentorship.user.entity.Mentor;
import com.global.mentorship.user.repo.MentorRepo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MentorService extends BaseService<Mentor, Long> {

	private final MentorRepo mentorRepo;
	
	
	public Page<MentorDto> findAllMentorsWithRating(
			Double rate ,Long categoryId,String name, int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return mentorRepo.findAllMentorsWithRating(rate ,categoryId, name,pageable);
	}
	
	public MentorInfoDto findMentorById(long id) {
		return mentorRepo.findMentorById(id);
	}

	public MentorInfoDto editMentorById(long id) {
		return null;
	}
	
}
