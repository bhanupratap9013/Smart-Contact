package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.SmartContactManagerDao;
import com.smart.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	SmartContactManagerDao smartContactManagerDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = smartContactManagerDao.getUserByUserName(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Username Not Found");
		}
		
		UserDetails userDetails = new CustomUserDetails(user);
		return userDetails;
	}

}
