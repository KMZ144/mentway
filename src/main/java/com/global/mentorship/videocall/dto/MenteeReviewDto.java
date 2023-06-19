package com.global.mentorship.videocall.dto;

import java.time.LocalDateTime;

import com.global.mentorship.base.dto.BaseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenteeReviewDto   {


	private long id;
	
	private String name;
	
	private String email;
	
	private String imgUrl;

	private double rate;

	private String details;
	
	private LocalDateTime endDate;
	

}
