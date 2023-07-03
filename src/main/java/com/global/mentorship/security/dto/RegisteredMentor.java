package com.global.mentorship.security.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisteredMentor  {

	private String name;
	
	@Email 
	private String email;
	
	private String password;

	private long categoryId;
	
	private String coverLetter;
	
	private MultipartFile img;
}
