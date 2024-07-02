package com.api.nagomin.entity;

import java.util.List;

import com.api.nagomin.util.StringListConverter;
import com.api.nagomin.util.Util;
import com.api.nagomin.util.Values.TORF;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false, unique = true)
	private String id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Convert(converter = StringListConverter.class)
	@Column(nullable = false)
	private List<String> interests;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TORF mbti;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TORF isVerified = TORF.F;

	@Column(nullable = false)
	private int createdAt;

	@Column(nullable = false)
	private int updatedAt;

	@Builder
	public User(Long seq, String id, String email, String password, List<String> interests, TORF mbti, TORF isVerified,
			int createdAt, int updatedAt) {
		super();
		this.seq = seq;
		this.id = id;
		this.email = email;
		this.password = password;
		this.interests = interests;
		this.mbti = mbti;
		this.isVerified = isVerified;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public void updateEmail(String email) {
		this.email = email;
		this.updatedAt = Util.createdAt();
	}
	
	public void updateIsVerified() {
		this.isVerified = TORF.T;
		this.updatedAt = Util.createdAt();
	}

	public void updatePassword(String password) {
		this.password = password;
		this.updatedAt = Util.createdAt();
	}
}
