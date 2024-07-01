package com.api.nagomin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {

	private String address;
	private String title;
	private String message;

	@Builder
	public MailDto(String address, String title, String message) {
		super();
		this.address = address;
		this.title = title;
		this.message = message;
	}
	
	public void setIdentityMessage(int randomNumber) {
		 this.message = "";
		 message += "<h3>" + "본인인증번호입니다." + "</h3>";
		 message += "<h1> " + randomNumber + " </h1>";
		 message += "<h3>" + "<b style='color: red'>5분이내</b>에 입력해주세요." + "</h3>";
		 
		 this.title = "[NAGOMIN] 본인인증";
	} 

}
