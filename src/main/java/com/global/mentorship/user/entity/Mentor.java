package com.global.mentorship.user.entity;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Mentor extends User{
	
	@Lob
	private String coverLetter;
	
	@Column(columnDefinition = "boolean default true")
	private boolean isValid;
	
	private String cvUrl;
	
	@ManyToOne
	private Category category;
	
	@OneToMany(mappedBy = "mentor", 
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<MentorMentees> mentorsMentees = new HashSet<>();

}
