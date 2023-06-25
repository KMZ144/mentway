package com.global.mentorship.user.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorEdit {

	private String name;
	
	@Email @Column(unique = true)
	private String email;
		
	private String imgUrl;

}
