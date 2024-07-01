package com.api.nagomin.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.nagomin.dto.ResultDto;
import com.api.nagomin.exception.AlreadyExistsException;
import com.api.nagomin.exception.DtoNullException;
import com.api.nagomin.exception.ExpiredDataException;
import com.api.nagomin.exception.InvalidDataException;
import com.api.nagomin.exception.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AlreadyExistsException.class)
    @ResponseBody
    public ResultDto<?> handleUserAlreadyExistsException(AlreadyExistsException ex) {
		return ResultDto.fail(ResponseCode.CONFLICT, null);
    }
	
	@ExceptionHandler(DtoNullException.class)
    @ResponseBody
    public ResultDto<?> handleDtoNullException(DtoNullException ex) {
		return ResultDto.fail(ResponseCode.CONFLICT, ex.getAdditionalInfo());
    }
	
	@ExceptionHandler(ExpiredDataException.class)
    @ResponseBody
    public ResultDto<?> handleExpiredCodeException(ExpiredDataException ex) {
		return ResultDto.fail(ResponseCode.UNAUTHORIZED, null);
    }
	
	@ExceptionHandler(InvalidDataException.class)
    @ResponseBody
    public ResultDto<?> handleInvalidDataException(InvalidDataException ex) {
		return ResultDto.fail(ResponseCode.BAD_DATA, null);
    }
	
	@ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResultDto<?> handleNotFoundException(NotFoundException ex) {
		return ResultDto.fail(ResponseCode.NOT_FOUND, null);
    }
}
