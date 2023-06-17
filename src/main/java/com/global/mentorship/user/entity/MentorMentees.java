package com.global.mentorship.user.entity;

import com.global.mentorship.base.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MentorMentees extends BaseEntity<Long> {
	
	
	private boolean inWishList;
	
	private int rating;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Mentor mentor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Mentee mentee;

}
