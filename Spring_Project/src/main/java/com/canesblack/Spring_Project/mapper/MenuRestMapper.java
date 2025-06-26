package com.canesblack.Spring_Project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.canesblack.Spring_Project.entity.Menu;

// @component와 비슷 => @componentScan
// @mapper => @mapperScan => 자동으로 스프링 컨테이너에 등록
@Mapper
public interface MenuRestMapper {
	
	//CRUD의 일부분
	@Select("SELECT idx,memID,title,content,writer,indate,count FROM backend_spring_project.menu ORDER BY idx DESC")
	public List<Menu>getLists();
	// 전체 게시물 정렬
	
	@Insert("INSERT INTO backend_spring_project.menu(memID,title,content,writer,indate) VALUES(#{memID},#{title},#{content},#{writer},#{indate})")
	public void boardInsert(Menu menu);
	@Select("SELECT idx,memID,title,content,writer,indate,count FROM backend_spring_project.menu WHERE idx=#{idx}")
	
	public Menu boardContent(int idx);
	@Delete("DELETE FROM backend_spring_project.menu WHERE idx=#{idx}")
	public void boardDelete(int idx);
	
	@Update("UPDATE backend_spring_project.menu SET title=#{title}, content=#{content}, writer=#{writer} WHERE idx = #{idx}")
	public void boardUpdate(Menu menu);
	
	@Update("UPDATE backend_spring_project.menu SET count = count+1 WHERE idx=#{idx}")
	public void boardCount(int idx);
}
