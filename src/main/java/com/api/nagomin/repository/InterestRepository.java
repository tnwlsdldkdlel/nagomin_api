package com.api.nagomin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.nagomin.entity.Interest;

public interface InterestRepository extends JpaRepository<Interest, String> {
	
	public List<Interest> findAll();
	
}
