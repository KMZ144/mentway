package com.global.mentorship.base.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseDto<ID extends Number> {
	
	private ID Id;

}
