package com.global.mentorship.videocall.entity;
import java.time.LocalDateTime;

import com.global.mentorship.base.entity.BaseEntity;
import com.global.mentorship.user.entity.Mentee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "CHECK (status IN ('PENDING', 'ACCEPTED' , 'REJECTED') default PENDING")
	private Status status;
	
	@Lob
	private String report;
	
	@Min(0) @Max(5)
	private int rate;
	
	private String uploadedUrl;
	
	private String meetingUrl;
	
	private LocalDateTime startDate;
	
	private boolean paidDone;
}


