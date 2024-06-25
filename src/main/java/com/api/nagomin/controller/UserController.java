package com.api.nagomin.controller;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.nagomin.dto.JoinDto;
import com.api.nagomin.dto.ResultDto;
import com.api.nagomin.entity.Interest;
import com.api.nagomin.entity.User;
import com.api.nagomin.repository.InterestRepository;
import com.api.nagomin.service.UserService;
import com.api.nagomin.util.ResponseCode;

import jakarta.validation.Valid;
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
	public ResultDto<List<Interest>> getInterest() {
		try {
			return ResultDto.success(interestRepository.findAll(), ResponseCode.SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

	@PostMapping("join")
	public ResultDto<User> save(@Valid @RequestBody JoinDto joinDto, BindingResult result) {
		isEmpty(result);
		
		return ResultDto.success(userService.save(joinDto), ResponseCode.SUCCESS.getMessage());
	}
}
