package com.global.mentorship.payment.entity;

import java.util.Date;

import com.global.mentorship.base.entity.BaseEntity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Transcations extends BaseEntity<Long> {
	
	private int amount;
	
	private Date date;

}
