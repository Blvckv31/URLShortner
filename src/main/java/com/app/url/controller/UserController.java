package com.app.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.url.entity.UserEntity;
import com.app.url.service.UserService;

@RestController
@RequestMapping("/admin/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/search")
	public ResponseEntity<?> getUser(@RequestParam String username) {
		UserEntity user = (UserEntity) userService.loadUserByUsername(username);
		if(user == null) {
			return ResponseEntity.status(404).body("User not found");
		}
		return ResponseEntity.status(200).body(user);
	}
	
	@DeleteMapping("delete")
	public ResponseEntity<?> deleteUser(@RequestParam long uid) {
		boolean resp = userService.deleteUser(uid);
		return (resp == false) ? ResponseEntity.status(404).body("User not found") :
			ResponseEntity.status(200).body("User deleted");
	}
}
