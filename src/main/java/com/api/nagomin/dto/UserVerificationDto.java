package com.api.nagomin.dto;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
@Getter
public class UserVerificationDto {
	
	private String code1;
	private String code2;
	private String code3;
	private String code4;
	private String email;
	
	@Builder
	public UserVerificationDto(String code1, String code2, String code3, String code4, String email) {
		super();
		this.code1 = code1;
		this.code2 = code2;
		this.code3 = code3;
		this.code4 = code4;
		this.email = email;
	}
	
	public String convertCode() {
		return code1.concat(code2).concat(code3).concat(code4);
	}

}
