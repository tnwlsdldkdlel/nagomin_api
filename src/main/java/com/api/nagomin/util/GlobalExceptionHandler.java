package com.api.nagomin.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.nagomin.dto.ResultDto;
import com.api.nagomin.exception.DtoNullException;
import com.api.nagomin.exception.UserAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseBody
    public ResultDto<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		return ResultDto.fail(ResponseCode.CONFLICT, null);
    }
	
	@ExceptionHandler(DtoNullException.class)
    @ResponseBody
    public ResultDto<?> handleDtoNullException(DtoNullException ex) {
		return ResultDto.fail(ResponseCode.CONFLICT, ex.getAdditionalInfo());
    }
}
