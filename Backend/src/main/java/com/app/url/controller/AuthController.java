package com.app.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.url.dto.LoginDto;
import com.app.url.entity.UserEntity;
import com.app.url.service.AuthService;
import com.app.url.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserEntity user) {
		UserEntity newUser = userService.createUser(user);
		return (newUser != null) ? ResponseEntity.status(201).body(newUser) : 
			ResponseEntity.status(400).body("Bad Request");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto request) {
		String resp = authService.authenticate(request.getUsername(), request.getPassword());
		return (resp != null) ? ResponseEntity.status(200).body(resp) :
			ResponseEntity.status(401).body("invalid credentials");
	}
}
