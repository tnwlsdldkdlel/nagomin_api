package com.api.nagomin.exception;

public class ExpiredDataException extends RuntimeException {
	public ExpiredDataException(String message) {
		super(message);
	}
}
