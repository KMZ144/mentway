package com.global.mentorship.base.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDto<ID extends Number> {
	
	private ID Id;

}
