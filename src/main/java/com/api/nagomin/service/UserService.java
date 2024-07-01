package com.api.nagomin.service;

import com.api.nagomin.dto.JoinDto;
import com.api.nagomin.dto.LoginDto;
import com.api.nagomin.dto.UpdateEmailDto;
import com.api.nagomin.dto.UserVerificationDto;

public interface UserService {
	
	public LoginDto save(JoinDto joinDto);
	
	public void validateEmail(UserVerificationDto userVerificationDto);
	
	public void resendEmail(String email);
	
	public LoginDto updateEmail(UpdateEmailDto updateEmailDto);
	
	public LoginDto login(LoginDto loginDto);

}
