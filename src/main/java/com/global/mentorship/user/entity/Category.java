package com.global.mentorship.user.entity;

import java.util.Set;

import com.global.mentorship.base.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category extends BaseEntity<Long> {
	
	private String name;
	
	

}
