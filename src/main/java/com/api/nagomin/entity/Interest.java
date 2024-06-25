package com.api.nagomin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Interest {

	@Id
	private String label;

	@Column(nullable = false)
	private String value;

	@Column(nullable = false)
	private String icon;

	@Builder
	public Interest(String label, String value, String icon) {
		super();
		this.label = label;
		this.value = value;
		this.icon = icon;
	}

}
