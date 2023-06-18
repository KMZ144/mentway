package com.global.mentorship.user.entity;


import com.global.mentorship.base.entity.BaseEntity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category extends BaseEntity<Long> {
	
	private String name;
	
	

}
