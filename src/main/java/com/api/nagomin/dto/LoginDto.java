package com.api.nagomin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

	private String id;
	private String password;
	private String jwt;
	
	@Builder
	public LoginDto(String id, String password, String jwt) {
		super();
		this.id = id;
		this.password = password;
		this.jwt = jwt;
	}	

}
