package com.api.nagomin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.nagomin.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByIdOrEmail(String id, String email);
	
}
