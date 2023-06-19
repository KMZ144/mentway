package com.global.mentorship.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.mentorship.user.entity.Category;
import com.global.mentorship.user.service.CategoryService;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;
	
	@GetMapping("")
	public ResponseEntity<List<Category>> findAllCategories (){
		return ResponseEntity.ok(categoryService.findAll());
	}

}
