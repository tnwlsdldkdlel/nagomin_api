package com.api.nagomin.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.nagomin.dto.FindInfoDto;
import com.api.nagomin.dto.JoinDto;
import com.api.nagomin.dto.LoginDto;
import com.api.nagomin.dto.NewPasswordDto;
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

	@GetMapping("validate/email/{email}")
	public ResultDto<?> resendEmail(@PathVariable(name = "email") String email) {
		userService.resendEmail(email);
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

	@PostMapping("info/id") // 아이디 찾기
	public ResultDto<?> findId(@RequestBody FindInfoDto findInfoDto) {
		return ResultDto.success(userService.findId(findInfoDto), ResponseCode.SUCCESS.getMessage());
	}
	
	@GetMapping("info/validate/email/{id}") // 이메일 보내기
	public ResultDto<?> sendEmail(@PathVariable(name = "id") String id) {
		return ResultDto.success(userService.sendEmail(id), ResponseCode.SUCCESS.getMessage());
	}
	
	@PostMapping("info/validate/email") // 인증코드 맞는지 확인
	public ResultDto<?> sendEmail(@RequestBody UserVerificationDto userVerificationDto) {
		return ResultDto.success(userService.validateEmailForLogin(userVerificationDto), ResponseCode.SUCCESS.getMessage());
	}
	
	@PostMapping("info/password") // 비밀번호 변경
	public ResultDto<?> resetPassword(@RequestBody NewPasswordDto newPasswordDto) {
		userService.resetPassword(newPasswordDto);
		return ResultDto.success(ResponseCode.SUCCESS.getMessage());
	}

	@PostMapping("login")
	public ResultDto<?> login(@RequestBody LoginDto loginDto) {
		return ResultDto.success(userService.login(loginDto), ResponseCode.SUCCESS.getMessage());
	}

}
