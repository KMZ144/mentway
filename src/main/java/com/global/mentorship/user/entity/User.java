package com.global.mentorship.user.entity;

import com.global.mentorship.base.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends BaseEntity<Long> {
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String imgUrl;
	
}
