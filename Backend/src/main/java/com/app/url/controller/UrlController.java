package com.app.url.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.url.dto.ShortenRequest;
import com.app.url.service.RateLimitService;
import com.app.url.service.UrlService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UrlController {

	@Autowired
	private UrlService service;
	
	@Autowired
	private RateLimitService rateLimitService;

	@PostMapping("/shorten")
	public ResponseEntity<?> shorten(@RequestBody ShortenRequest request) {
		String code = service.shorten(request.getUrl());

		return ResponseEntity.ok(Map.of("shortUrl", code));
	}

	@GetMapping("/{code}")
	public ResponseEntity<?> redirect(@PathVariable String code, HttpServletRequest request) {
		String ip = request.getRemoteAddr();

		if (!rateLimitService.allowRequest(ip)) {
			return ResponseEntity.status(429).body("Too many requests");
		}
		String url = service.getLongUrl(code);

		return ResponseEntity.status(302).header("Location", url).build();
	}
}