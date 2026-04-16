package com.app.url.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.url.entity.UserEntity;
import com.app.url.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
		user.setPassword(passwordEncoder.encode(body.getPassword()));
		user.setAuthorities("ROLE_USER");
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
		    return null;
		}
		return userRepository.save(user);
	}
	
	public boolean deleteUser(long id) {
		try {
			userRepository.deleteById(id);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username).get();
		return user;
	}
}
