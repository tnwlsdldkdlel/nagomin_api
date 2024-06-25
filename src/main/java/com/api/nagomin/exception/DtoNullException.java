package com.api.nagomin.exception;

import java.util.Map;

public class DtoNullException extends RuntimeException {
	private Map<String, Object> additionalInfo;

	public DtoNullException(String message, Map<String, Object> additionalInfo) {
		super(message);
		this.additionalInfo = additionalInfo;
	}

	public Map<String, Object> getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(Map<String, Object> additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
}
