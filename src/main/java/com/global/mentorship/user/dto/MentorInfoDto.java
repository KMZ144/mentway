package com.global.mentorship.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MentorInfoDto {
	
//	private Double averageRate;
	private Long id;
	private String name;
	private String imgUrl;
    private String categoryName;
    private String email;
    private String coverLetter;

	

}
