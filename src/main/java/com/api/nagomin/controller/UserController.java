package com.api.nagomin.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.nagomin.dto.JoinDto;
import com.api.nagomin.dto.ResultDto;
import com.api.nagomin.dto.UpdateEmailDto;
import com.api.nagomin.dto.UserVerificationDto;
import com.api.nagomin.repository.InterestRepository;
import com.api.nagomin.service.UserService;
import com.api.nagomin.util.ResponseCode;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/v1/user/*")
@RequiredArgsConstructor
@Log4j2
public class UserController extends BaseController {

	private final InterestRepository interestRepository;
	private final UserService userService;

	@GetMapping("interest")
	public ResultDto<?> getInterest() {
		try {
			return ResultDto.success(interestRepository.findAll(), ResponseCode.SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

	@PostMapping("join")
	public ResultDto<?> save(@RequestBody JoinDto joinDto, BindingResult result, HttpServletResponse response) {
        return ResultDto.success(userService.save(joinDto), ResponseCode.SUCCESS.getMessage());
	}

	@PostMapping("validate/email")
	public ResultDto<?> validateEmail(@RequestBody UserVerificationDto userVerificationDto) {
		userService.validateEmail(userVerificationDto);
		return ResultDto.success(ResponseCode.SUCCESS.getMessage());
	}

	@PutMapping("validate/email")
	public ResultDto<?> updateEmail(@RequestBody UpdateEmailDto updateEmail) {
		return ResultDto.success(userService.updateEmail(updateEmail), ResponseCode.SUCCESS.getMessage());
	}
	
	@GetMapping("info")
	public ResultDto<?> info(@RequestBody UpdateEmailDto updateEmail) {
		userService.updateEmail(updateEmail);
		return ResultDto.success(ResponseCode.SUCCESS.getMessage());
	} 
	
}
