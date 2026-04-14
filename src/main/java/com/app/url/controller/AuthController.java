package com.app.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.url.entity.UserEntity;
import com.app.url.service.UserService;

@RestController
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserEntity user) {
		UserEntity newUser = userService.createUser(user);
		return (newUser != null) ? ResponseEntity.status(201).body(newUser) : 
			ResponseEntity.status(400).body("Bad Request");
	}
}
