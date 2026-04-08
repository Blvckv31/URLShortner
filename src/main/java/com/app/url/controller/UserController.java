package com.app.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.url.entity.UserEntity;
import com.app.url.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{uid}")
	public ResponseEntity<?> getUser(@PathVariable long uid) {
		UserEntity user = userService.findUserById(uid);
		if(user == null) {
			return ResponseEntity.status(404).body("User not found");
		}
		return ResponseEntity.status(200).body(user);
	}
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserEntity request) {
		UserEntity user = userService.createUser(request);
		return (user != null) ? ResponseEntity.status(201).body(user) : 
			ResponseEntity.status(400).body("Bad Request");
	}

}
