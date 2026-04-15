package com.app.url.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.url.entity.UserEntity;

import jakarta.annotation.Nullable;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	@Nullable
	Optional<UserEntity> findById(long id);
	
	@Nullable
	Optional<UserEntity> findByUsername(String username);
}