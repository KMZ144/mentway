package com.global.mentorship.user.entity;

import java.util.HashSet;
import java.util.Set;

import com.global.mentorship.videocall.entity.MenteesServices;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Mentee extends User {

	@OneToMany(mappedBy = "mentee", 
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<MentorMentees> mentorsMentees = new HashSet<>();
	
	@OneToMany(mappedBy = "mentee", 
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<MenteesServices> menteesServices = new HashSet<>();

}
