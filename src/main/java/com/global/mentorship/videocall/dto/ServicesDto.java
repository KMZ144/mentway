package com.global.mentorship.videocall.dto;

import com.global.mentorship.base.dto.BaseDto;
import com.global.mentorship.user.dto.MentorDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServicesDto extends BaseDto<Long> {
	
	private String title;
	
	private String details;
	
	@Min(0)
	private int price;
	
	@Min(0) @Max(3)
	private int duration;

}
