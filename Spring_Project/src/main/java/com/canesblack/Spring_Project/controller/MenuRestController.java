package com.canesblack.Spring_Project.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.canesblack.Spring_Project.service.MenuRestService;



import com.canesblack.Spring_Project.entity.Menu;

//RestAPI를 개발할 때 사용
@RestController
public class MenuRestController {
	
	@Autowired
	private MenuRestService menuRestService;
	
	//1. 메뉴 (모든 게시판) 조회 : 모든 게시판을 가져온다.
	@GetMapping("/menu/all")
	public ResponseEntity<List<Menu>>getAllMenus(){
		List<Menu>menus = menuRestService.getLists();
		if(menus != null && !menus.isEmpty()) {
			return ResponseEntity.ok(menus);
		} else {
			return ResponseEntity.noContent().build(); // 상태코드 2041번 NoContent 반환
		}
	}
	
	//2. 메뉴(한개의 게시판 생성) 생성
	@PostMapping("/menu/add")
	public ResponseEntity<String>addMenu(@RequestBody Menu menu){
		
		// 작성된 시점의 날짜를 자동 설정
		if(menu.getIndate() == null || menu.getIndate().isEmpty()) {
			menu.setIndate(LocalDate.now().toString());
		}
		// 조회수는 처음에 0으로 설정. (mySQL)에서 설정해서 안해도 괜찮을 듯 (추측)
		menu.setCount(0);
		
		// 메뉴를 데이터베이스에 삽입
		menuRestService.boardInsert(menu);
		return ResponseEntity.ok("게시글 작성됨");
	}
	
	// 3. 메뉴 (한개의 게시판 수정) 수정
	// localhost:8080/menu/update/2
	@PutMapping("/menu/update/{idx}")
	public void updateMenu(@RequestBody Menu menu, @PathVariable("idx") int idx) {
		menu.setIdx(idx); //특정 idx를 가진 게시글을 menu안의 title과 content를 가져와서 수정해준다.
		menuRestService.boardUpdate(menu);
	}
	
	// 4.메뉴(한개의 게시판 삭제) 삭제
	@DeleteMapping("/menu/delete/{idx}")
	public void deleteMenu(@PathVariable("idx") int idx) {
		menuRestService.boardDelete(idx);
	}
	
	// 5.특정메뉴(한개의 게시판의 내용을 조회) 조회
	@GetMapping("/menu/{idx}")
	public ResponseEntity<Menu>getMenuById(@PathVariable("idx") int idx){
		Menu menu = menuRestService.boardContent(idx); 
		//200번대의 상태코드와 menu 객체를 백엔드에서 프론트엔드 영역으로 데이터를 넘긴다.
		if(menu != null) {
			return ResponseEntity.ok(menu);
	} else {
		return ResponseEntity.notFound().build();
		// 메뉴가 존재하지 않을 경우 not found -> 404에러를 반환하게끔 설정
	}
}
	
	// 6. 특정 메뉴(게시판 조회수 증가) 조회수 증가
	@PutMapping("/menu/count/{idx}")
	public void incrementMenuCount(@PathVariable("idx") int idx) {
		menuRestService.boardCount(idx);
	}
	
}