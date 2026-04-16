package com.app.url.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.app.url.util.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtUtil;

	public String authenticate(String username, String password) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
		return (authentication.isAuthenticated()) ? jwtUtil.generateToken(username) :
			null;
	}

}
