package com.app.url.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.url.entity.UserEntity;
import com.app.url.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public boolean checkUserExists(String username) {
		Optional<UserEntity> user = userRepository.findByUsername(username);
		return (user.orElse(null) == null) ? false : true;
	}
	
	public UserEntity findUserById(long id) {
		Optional<UserEntity> user = userRepository.findById(id);
		return user.orElse(null);
	}
	
	public UserEntity createUser(UserEntity body) {
		UserEntity user = new UserEntity();
		user.setUsername(body.getUsername());
		user.setPassword(body.getPassword());
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		if (checkUserExists(user.getUsername()) == false) {
			user = userRepository.save(user);
			return user;
		}
		return null;
	}
}
