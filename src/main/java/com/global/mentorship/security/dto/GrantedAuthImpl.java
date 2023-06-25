package com.global.mentorship.security.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.global.mentorship.user.entity.Roles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component
@NoArgsConstructor
public class GrantedAuthImpl implements GrantedAuthority{

	
	private Roles role;
	
	public GrantedAuthImpl(Roles role) {
		this.role = role ;
	}
	
	@Override
	public String getAuthority() {
		return role.getName();
	}

}
