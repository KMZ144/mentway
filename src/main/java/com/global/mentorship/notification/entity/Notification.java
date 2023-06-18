package com.global.mentorship.notification.entity;

import java.util.Date;

import com.global.mentorship.base.entity.BaseEntity;
import com.global.mentorship.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notification extends BaseEntity<Long> {
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User sender;
	
	private String content;
	
	private String type;
	
	private Date date;
	
	

}
