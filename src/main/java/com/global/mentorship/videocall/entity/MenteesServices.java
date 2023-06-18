package com.global.mentorship.videocall.entity;
import java.time.LocalDateTime;

import com.global.mentorship.base.entity.BaseEntity;
import com.global.mentorship.user.entity.Mentee;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MenteesServices extends BaseEntity<Long> {

	@ManyToOne(fetch = FetchType.LAZY)
	private Mentee mentee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Services services;
	
	private String applicationDetails;
	
	private boolean isAccepted;
	
	@Lob
	private String report;
	
	
	private int rate;
	
	private String uploadedUrl;
	
	private String meetingUrl;
	
}
