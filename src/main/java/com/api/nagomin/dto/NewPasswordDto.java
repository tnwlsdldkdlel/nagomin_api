package com.api.nagomin.dto;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Component
@Getter
@ToString
public class NewPasswordDto {

	private String password1;
	private String password2;
	private Long seq;

	@Builder
	public NewPasswordDto(String password1, String password2, Long seq) {
		super();
		this.password1 = password1;
		this.password2 = password2;
		this.seq = seq;
	}

}
