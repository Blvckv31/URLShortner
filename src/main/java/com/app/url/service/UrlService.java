package com.app.url.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.url.entity.UrlEntity;
import com.app.url.repository.UrlRepository;

@Service
public class UrlService {

	@Autowired
	private UrlRepository repository;

	public String shorten(String longUrl) {

		if (longUrl == null || longUrl.isBlank()) {
			throw new IllegalArgumentException("Invalid URL");
		}

		return repository.findByLongUrl(longUrl).map(UrlEntity::getShortCode).orElseGet(() -> {
			UrlEntity url = new UrlEntity();
			url.setLongUrl(longUrl);
			url.setCreatedAt(LocalDateTime.now());

			UrlEntity saved = repository.save(url);

			String shortCode = encode(saved.getId());
			saved.setShortCode(shortCode);

			repository.save(saved);

			return shortCode;
		});
	}

	public String getLongUrl(String shortCode) {
		return repository.findByShortCode(shortCode).orElseThrow(() -> new RuntimeException("URL not found"))
				.getLongUrl();
	}

	private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private String encode(Long id) {
		StringBuilder sb = new StringBuilder();
		while (id > 0) {
			sb.append(BASE62.charAt((int) (id % 62)));
			id /= 62;
		}
		return sb.reverse().toString();
	}
}
