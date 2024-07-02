package com.api.nagomin.service;

import java.util.Map;

import com.api.nagomin.dto.FindInfoDto;
import com.api.nagomin.dto.JoinDto;
import com.api.nagomin.dto.LoginDto;
import com.api.nagomin.dto.NewPasswordDto;
import com.api.nagomin.dto.UpdateEmailDto;
import com.api.nagomin.dto.UserVerificationDto;

public interface UserService {

	public LoginDto save(JoinDto joinDto);

	public void validateEmail(UserVerificationDto userVerificationDto);

	public void resendEmail(String email);

	public LoginDto updateEmail(UpdateEmailDto updateEmailDto);

	public LoginDto login(LoginDto loginDto);

	public Map<String, Object> findId(FindInfoDto findInfoDto);

	public Map<String, Object> sendEmail(String id);

	public void resetPassword(NewPasswordDto newPasswordDto);
	
	public Map<String, Object> validateEmailForLogin(UserVerificationDto userVerificationDto);

}
