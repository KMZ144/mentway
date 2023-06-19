package com.global.mentorship.user.dto;

import com.global.mentorship.base.dto.BaseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MenteeDto extends BaseDto<Long> {

	private String name;
	
	private String email;
	
	private String imgUrl;

	
}
