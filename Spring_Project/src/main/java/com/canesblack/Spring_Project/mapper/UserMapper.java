package com.canesblack.Spring_Project.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.canesblack.Spring_Project.entity.User;

@Mapper
// 자동으로 @Component기능 비슷하게 스프링컨테이너에 등록이 됨 (인터페이스)
// Java언어와 Mysql 언어를 연결해주는 역할
public interface UserMapper {
	
	// CRUD의 CREATE에 해당하는 기능 중 하나	
	@Insert("INSERT INTO backend_spring_project.user(username,password,writer,role) VALUES (#{username},#{password},#{writer},#{role})")
	// void => DB에서 backend영역(Spring framework)으로 데이터를 가져오는게 없기 때문에, void(빈값)로 가져오는게 없다고 작성.
	void insertUser(User user);
	@Select("SELECT username,password,writer,role FROM backend_spring_project.user WHERE username = #{username}")
	User findByUsername(String username);
	
	@Select("SELECT writer FROM backend_spring_project.user WHERE username = #{username}")
	String findWriter(String username);
	
	// CRUD의 UPDATE에 해당하는 기능 중 하나
	// @Update()
	// CRUD의 DELETE에 해당하는 기능 중 하나	
	// @Delete()
}
