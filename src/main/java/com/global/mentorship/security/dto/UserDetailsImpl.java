package com.global.mentorship.security.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.global.mentorship.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
	
	private long id;
	
	private String email;
	
	private String password;
	
	private String imgUrl;
	
	private String name;
	
	private boolean hasPayment; 
	
	private Collection<GrantedAuthority> authorities;
	
	public UserDetailsImpl (User user) {
		this.id=user.getId();
		this.email = user.getEmail();
		this.imgUrl = user.getImgUrl();
		this.password = user.getPassword();
		this.name =user.getName();
		this.authorities = user.getRoles().stream()
				.map(role -> new GrantedAuthImpl(role)).collect(Collectors.toList()) ;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;  
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
