package com.global.mentorship.videocall.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.global.mentorship.base.entity.BaseEntity;
import com.global.mentorship.user.entity.Mentor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Services extends BaseEntity<Long> {
	
	private String title;
	
	private String details;
	
	@Min(0)
	private int price;
	
	@Min(0) @Max(3)
	private int duration;
	
	@OneToMany(mappedBy = "services", 
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<MenteesServices> menteesServices = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	private Mentor mentor;
}
