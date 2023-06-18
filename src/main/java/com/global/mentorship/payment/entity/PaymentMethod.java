package com.global.mentorship.payment.entity;

import com.global.mentorship.base.entity.BaseEntity;
import com.global.mentorship.user.entity.User;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PaymentMethod extends BaseEntity<Long> {
	
	private User user; 
	
	private String type;
	
	private String info;

}
