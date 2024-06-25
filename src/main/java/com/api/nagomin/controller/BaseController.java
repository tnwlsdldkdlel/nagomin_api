package com.api.nagomin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;

import com.api.nagomin.dto.ResultDto;
import com.api.nagomin.exception.DtoNullException;

public class BaseController {

	public ResultDto<Map<String, Object>> isEmpty(BindingResult result) {
		if (result.getErrorCount() > 0) {
			Map<String, Object> message = new HashMap<>();

			result.getFieldErrors().forEach(error -> {
				message.put(error.getField(), error.getDefaultMessage());
			});

			throw new DtoNullException("유효하지 않은 데이터.", message);
		}
		
		return null;
	}

}
