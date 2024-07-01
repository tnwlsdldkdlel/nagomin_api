package com.api.nagomin.util;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ResponseCode {

	// 500 Internal Server Error
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "오류가 발생."),

	// 200 
	SUCCESS(HttpStatus.OK, true, "데이터 조회 성공."),
	
	// 400 BAD_REQUEST
	BAD_REQUEST(HttpStatus.BAD_REQUEST, true, "잘못된 요청."),
	BAD_DATA(HttpStatus.BAD_REQUEST, false, "유효하지 않은 데이터."),
	
	// 401 UNAUTHORIZED
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, false, "만료된 데이터."),
	
	// 404 Not Found
	NOT_FOUND(HttpStatus.NOT_FOUND, false, "존재하지 않은 데이터."),
	
	// 409 CONFLICT
	CONFLICT(HttpStatus.CONFLICT, false, "데이터 중복.");

	private final HttpStatus httpStatus;
	private final Boolean success;
	private final String message;

	public int getHttpStatusCode() {
		return httpStatus.value();
	}
}
