package com.api.nagomin.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.api.nagomin.util.Values.TORF;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Component
@Getter
public class JoinDto {

	private Long seq;
	private String id;
	private String email;
	private String password;
	private List<String> interests;
	private TORF mbti;

	@Builder
	public JoinDto(Long seq, String id, String email, String password, List<String> interests, TORF mbti) {
		super();
		this.seq = seq;
		this.id = id;
		this.email = email;
		this.password = password;
		this.interests = interests;
		this.mbti = mbti;
	}

}
