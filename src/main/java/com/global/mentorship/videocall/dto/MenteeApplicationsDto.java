package com.global.mentorship.videocall.dto;

import java.time.LocalDateTime;

import com.global.mentorship.videocall.entity.Services;
import com.global.mentorship.videocall.entity.Status;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenteeApplicationsDto {
	
	private long applicationId;
	
	private String title;
	
	private String details;
	
	@Min(0)
	private int price;
	
	@Min(0) @Max(3)
	private int duration;
	
	private String applicationDetails;
	
	private LocalDateTime startDate;
	
	private Long mentorId;
	private String mentorName;
	private String mentorImgUrl;
    private String categoryName;

    private Status status;
}
