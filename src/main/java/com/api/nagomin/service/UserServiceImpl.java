package com.api.nagomin.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.nagomin.dto.JoinDto;
import com.api.nagomin.entity.User;
import com.api.nagomin.exception.UserAlreadyExistsException;
import com.api.nagomin.repository.UserRepository;
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

	@Transactional
	@Override
	public User save(JoinDto joinDto) {
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
			throw new UserAlreadyExistsException("존재하는 유저.");
		} else {
			return userRepository.save(user);
		}	
	}

}
