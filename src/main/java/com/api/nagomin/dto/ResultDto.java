package com.api.nagomin.dto;

import com.api.nagomin.util.ResponseCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Builder
@RequiredArgsConstructor
@ToString
@Getter
public class ResultDto<T> {

	private int code;
	private String msg;
	private T data;

	private ResultDto(int code, String msg, T data) {
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

	public static <T> ResultDto<T> success(T data, String message) {
		return new ResultDto<T>(200, message, data);
	}
	
	public static <T> ResultDto<T> success(String message) {
		return new ResultDto<T>(200, message, null);
	}

	public static <T> ResultDto<T> fail(ResponseCode responseCode, T data) {
		return new ResultDto<T>(responseCode.getHttpStatusCode(), responseCode.getMessage(), data);
	}
	
	public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
