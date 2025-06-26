package com.canesblack.Spring_Project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.canesblack.Spring_Project.entity.Role;
import com.canesblack.Spring_Project.entity.User;
import com.canesblack.Spring_Project.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public String register(@ModelAttribute User user) {
		String userPassword = user.getPassword();
		user.setRole(Role.MEMBER);
		//password 암호화
		String passwordEncoded = passwordEncoder.encode(userPassword);
		user.setPassword(passwordEncoded);
		userService.insertUser(user);
		return "redirect:/loginPage";
	}
	
	
}
