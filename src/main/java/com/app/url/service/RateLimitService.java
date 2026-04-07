package com.app.url.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RateLimitService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final int LIMIT = 10;
    private static final int WINDOW_SECONDS = 60;

    public boolean allowRequest(String ip) {

        String key = "rate_limit:" + ip;
        long now = System.currentTimeMillis();
        long windowStart = now - (WINDOW_SECONDS * 1000);

        redisTemplate.opsForZSet().removeRangeByScore(key, 0, windowStart);

        Long count = redisTemplate.opsForZSet().size(key);

        if (count != null && count >= LIMIT) {
            return false;
        }

        redisTemplate.opsForZSet().add(key, String.valueOf(now), now);

        redisTemplate.expire(key, Duration.ofSeconds(WINDOW_SECONDS));

        return true;
    }
}