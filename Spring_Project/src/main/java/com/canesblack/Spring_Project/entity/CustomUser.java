package com.canesblack.Spring_Project.entity;

import org.springframework.security.core.authority.AuthorityUtils;

public class CustomUser extends org.springframework.security.core.userdetails.User {

	private User user;
	
	public CustomUser(User user) {
		super(user.getUsername(),user.getPassword(),AuthorityUtils.createAuthorityList(user.getRole().toString()));
		
	}	
}
