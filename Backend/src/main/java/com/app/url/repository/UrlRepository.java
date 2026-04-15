package com.app.url.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.url.entity.UrlEntity;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    Optional<UrlEntity> findByShortCode(String shortCode);

    Optional<UrlEntity> findByLongUrl(String longUrl);
}