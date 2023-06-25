package com.global.mentorship.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.global.mentorship.security.dto.UserDetailsImpl;
import com.global.mentorship.user.entity.User;
import com.global.mentorship.user.repo.UserRepo;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private  UserRepo userRepo;
	
	@Override
	public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepo.findByEmail(email);
		
		if (user==null) {
			throw new UsernameNotFoundException("this account is not found");
		}
		
		return new UserDetailsImpl(user);
	}

}
