package com.global.mentorship.user.dto;

import com.global.mentorship.base.dto.BaseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MentorDto extends BaseDto<Long> {
	
	private Double averageRate;
	private Long id;
	private String name;
	private String imgUrl;
    private String categoryName;
}
