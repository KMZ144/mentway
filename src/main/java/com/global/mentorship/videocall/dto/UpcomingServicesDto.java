package com.global.mentorship.videocall.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpcomingServicesDto {
	
	private long applicationId;
	private long serviceId;
	private String meetingUrl;
	private int duration;
	private long id;
	private String name;
	private String imgUrl;
	private String serviceDetails; 
	private LocalDateTime startDate;

}
