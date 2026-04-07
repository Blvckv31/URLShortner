package com.app.url.service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.filters.ExpiresFilter.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.app.url.entity.UrlEntity;
import com.app.url.repository.UrlRepository;


@Service
public class UrlService {

	@Autowired
	private UrlRepository repository;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

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
		// 1. Check cache
		String cached = redisTemplate.opsForValue().get(shortCode);
		if (cached != null) {
			return cached;
		}

		// 2. Fallback to DB
		String url = repository.findByShortCode(shortCode).orElseThrow(() -> new RuntimeException("Not found"))
				.getLongUrl();

		// 3. Store in cache
		redisTemplate.opsForValue().set(shortCode, url, 12, TimeUnit.HOURS);

		return url;
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
