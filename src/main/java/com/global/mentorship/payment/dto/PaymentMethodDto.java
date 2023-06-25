package com.global.mentorship.payment.dto;

import com.global.mentorship.base.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodDto extends BaseDto<Long> {

	private String type;
	
	private String info;
}
