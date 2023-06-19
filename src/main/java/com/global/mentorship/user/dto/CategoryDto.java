package com.global.mentorship.user.dto;

import com.global.mentorship.base.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto extends BaseDto<Long> {
	
	private String categoryName;

}
