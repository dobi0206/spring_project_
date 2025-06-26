package com.canesblack.Spring_Project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canesblack.Spring_Project.entity.User;
import com.canesblack.Spring_Project.mapper.UserMapper;

@Service
public class UserService {
	// DI 의존성 주입
	@Autowired
	private UserMapper userMapper;
	
	public void insertUser(User user) {
		userMapper.insertUser(user);
	}
	
	public String findWriter(String username) {
		return userMapper.findWriter(username);
	}
	
	
}
