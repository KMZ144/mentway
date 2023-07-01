package com.global.mentorship.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StripeTokenDto {
	
	private String cardNumber;
	
	private String expMonth;
	
	private String expYear;
	
	private String token;
	
	private String cvc;


}
