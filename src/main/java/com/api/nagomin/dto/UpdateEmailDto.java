package com.api.nagomin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateEmailDto {

	private String preEmail;
	private String updateEmail;

	@Builder
	public UpdateEmailDto(String preEmail, String updateEmail) {
		super();
		this.preEmail = preEmail;
		this.updateEmail = updateEmail;
	}

}
