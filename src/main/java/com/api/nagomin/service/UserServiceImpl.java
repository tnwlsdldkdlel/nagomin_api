package com.api.nagomin.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nagomin.dto.JoinDto;
import com.api.nagomin.dto.LoginDto;
import com.api.nagomin.dto.MailDto;
import com.api.nagomin.dto.UpdateEmailDto;
import com.api.nagomin.dto.UserVerificationDto;
import com.api.nagomin.entity.User;
import com.api.nagomin.exception.AlreadyExistsException;
import com.api.nagomin.exception.ExpiredDataException;
import com.api.nagomin.exception.InvalidDataException;
import com.api.nagomin.repository.UserRepository;
import com.api.nagomin.security.CustomUser;
import com.api.nagomin.util.JwtUtils;
import com.api.nagomin.util.RedisUtils;
import com.api.nagomin.util.Util;
import com.api.nagomin.util.Values.TORF;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;
	private final RedisUtils redisUtils;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtUtils jwtUtils;

	@Transactional
	@Override
	public LoginDto save(JoinDto joinDto) {
		// 유저 확인
		User user = User.builder()
				.id(joinDto.getId())
				.email(joinDto.getEmail())
				.password(passwordEncoder.encode(joinDto.getPassword()))
				.interests(joinDto.getInterests())
				.mbti(joinDto.getMbti())
				.createdAt(Util.createdAt())
				.updatedAt(Util.createdAt())
				.isVerified(TORF.F)
				.build();
		
		if(userRepository.existsByIdOrEmail(joinDto.getId(), joinDto.getEmail())) {
			throw new AlreadyExistsException("존재하는 유저.");
		} else {
			 User loginUser = userRepository.save(user); 
			 int randomNumber = (int) (1000 + Math.random() * 9000);
			 
			 // redis저장 - 5분의 유효시간
			 redisUtils.setDataExpire(joinDto.getEmail(), String.valueOf(randomNumber), 5);
			
			// 본인인증용 확인 메일 발송.
			MailDto emailDto = MailDto.builder()
					.address(joinDto.getEmail())
					.build();
			emailDto.setIdentityMessage(randomNumber);
			mailService.mailSend(emailDto);
			
			// 회원가입하면 바로 로그인 구현.
			LoginDto loginDto = LoginDto.builder()
					.id(joinDto.getId())
					.password(joinDto.getPassword())
					.build();
			
			return login(loginDto);
		}	
	}

	@Transactional
	@Override
	public void validateEmail(UserVerificationDto userVerificationDto) {
		String code = userVerificationDto.convertCode();
		
		// 해당 key가 없으면 만료된 코드, 있으면 같은지 확인.
		if(redisUtils.existData(userVerificationDto.getEmail())) {
			String checkCode = redisUtils.getData(userVerificationDto.getEmail());
			
			if(!checkCode.equals(code)) {
				throw new InvalidDataException("유효하지않은 코드.");
			} else {
				User user = userRepository.findByEmail(userVerificationDto.getEmail());
				user.updateIsVerified();
			}
		} else {
			throw new ExpiredDataException("만료된 코드");
		}
		
	}

	@Override
	public void resendEmail(String email) {
		// 다시 보내기전에 redis에 key가 있는지 확인후 있으면 삭제 없으면 다시.
		if(redisUtils.existData(email)) {
			redisUtils.deleteData(email);
		}
		
		int randomNumber = (int) (1000 + Math.random() * 9000);
		 
		 // redis저장 - 5분의 유효시간
		 redisUtils.setDataExpire(email, String.valueOf(randomNumber), 5);
		 
		// 본인인증용 확인 메일 발송.
		MailDto emailDto = MailDto.builder()
				.address(email)
				.build();
		emailDto.setIdentityMessage(randomNumber);
		mailService.mailSend(emailDto);			
	}

	@Transactional
	@Override
	public LoginDto updateEmail(UpdateEmailDto updateEmailDto) {
		CustomUser customUser = Util.getLoginUserInfo();
		
		// 업데이트 하기전 redis에 key있으면 삭제.
		if(redisUtils.existData(updateEmailDto.getPreEmail())) {
			redisUtils.deleteData(updateEmailDto.getPreEmail());
		}
			
		// 업데이트할 이메일이 이미 존재하는 경우.
		if(userRepository.existsByEmail(updateEmailDto.getUpdateEmail())) {
			throw new AlreadyExistsException("존재하는 유저.");
		} else {
			// 이메일 업데이트.
			User user = userRepository.findById(customUser.getSeq())
					.orElseThrow(() -> new InvalidDataException("유효하지 않은 데이터"));
			
			user.updateEmail(updateEmailDto.getUpdateEmail());
			customUser.setEmail(updateEmailDto.getUpdateEmail());
			
			// 이메일 다시 보내기.
			resendEmail(updateEmailDto.getUpdateEmail());
			
			// jwt 토큰 변경.
			LoginDto loginDto = new LoginDto();
		    loginDto.setId(user.getId());
		    loginDto.setJwt(jwtUtils.generateToken(customUser.getClaims()));
			
			return loginDto;
		}
	}

	@Override
	public LoginDto login(LoginDto loginDto) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getId(), loginDto.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		loginDto.setJwt(jwtUtils.generateToken(customUser.getClaims()));
		
		return loginDto;
	}

}
