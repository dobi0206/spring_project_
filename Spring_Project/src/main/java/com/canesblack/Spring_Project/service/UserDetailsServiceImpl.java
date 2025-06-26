package com.canesblack.Spring_Project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.canesblack.Spring_Project.entity.CustomUser;
import com.canesblack.Spring_Project.entity.User;
import com.canesblack.Spring_Project.mapper.UserMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userMapper.findByUsername(username);
		if(user==null) {
			// 데이터가 없을 시
			throw new UsernameNotFoundException(username+"존재하지 않습니다.");
		}
		// 로그인 했을 때 DB에 유저 정보가 존재한다면
		return new CustomUser(user);
	}
	
	
}
