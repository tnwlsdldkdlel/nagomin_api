package com.api.nagomin.dto;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Component
@Getter
public class FindInfoDto {

	private String email;
	private String id;
	
	@Builder
	public FindInfoDto(String email, String id) {
		super();
		this.email = email;
		this.id = id;
	}
	
}
