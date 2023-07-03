package com.global.mentorship.videocall.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.global.mentorship.user.entity.Mentee;
import com.global.mentorship.videocall.entity.Services;
import com.global.mentorship.videocall.entity.Status;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenteeServicesDto {
	
	private long applicationId;

	private long  menteeId;

	private String name;
	
	private String imgUrl;
	
	private long servicesId;
	
	private String applicationDetails;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startDate;

	private Status status;
}
