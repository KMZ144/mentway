package com.global.mentorship.videocall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpcomingServicesDto {
	
	private long serviceId;
	private String meetingUrl;
	private int duration;
	private String name;
	private String imgUrl;
	private String serviceDetails; 

}
