package com.global.mentorship.notification.entity;

import com.global.mentorship.base.entity.BaseEntity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Subscribers extends BaseEntity<Long> {
	
	private String typeOfSubscrubtion; 
	

}
