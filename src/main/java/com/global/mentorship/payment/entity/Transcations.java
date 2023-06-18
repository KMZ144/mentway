package com.global.mentorship.payment.entity;

import java.time.LocalDateTime;

import com.global.mentorship.base.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Transcations extends BaseEntity<Long> {
	
	@ManyToOne(fetch = FetchType.LAZY)
	private PaymentMethod paymentMethod;
	
	private int amount;
	
	private LocalDateTime date;

}
