package com.app.url.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String signature;

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(Keys.hmacShaKeyFor(signature.getBytes()), SignatureAlgorithm.HS256).compact();
	}

	public String extractUsername(String token) {
		String username = Jwts.parserBuilder().setSigningKey(signature.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
		return username;
	}

	public boolean validateToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(signature.getBytes()).build().parseClaimsJws(token)
				.getBody();
		return !claims.getExpiration().before(new Date());
	}
}
