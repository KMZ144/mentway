package com.global.mentorship.security.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResposne {
	
	
	private String jwt;
	
	private String name;
	
	private String email;
	
	private String imgUrl;
	
	private long id;
	
	private Collection<String> authorities;
	
	private boolean hasValidPaymentMethod;

}
