package com.global.mentorship.videocall.dto;

import com.global.mentorship.base.dto.BaseDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicesDto extends BaseDto<Long> {
	
	private String title;
	
	private String details;
	
	@Min(0)
	private int price;
	
	@Min(0) @Max(3)
	private int duration;

}
