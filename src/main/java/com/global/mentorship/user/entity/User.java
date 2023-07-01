package com.global.mentorship.user.entity;


import java.util.Set;

import com.global.mentorship.base.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public  class User extends BaseEntity<Long> {
	
	private String name;
	
	@Email @Column(unique = true)
	private String email;
	
	private String password;
	
	private String imgUrl;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Roles> roles;
	
	private String stripeId;
	
	@Column(columnDefinition = "boolean default false")
	private boolean hasValidPayment;
}
