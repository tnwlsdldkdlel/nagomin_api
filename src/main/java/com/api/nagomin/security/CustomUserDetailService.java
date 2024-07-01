package com.api.nagomin.security;

import java.util.Arrays;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.api.nagomin.entity.User;
import com.api.nagomin.exception.NotFoundException;
import com.api.nagomin.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserDetailService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findById(username);
		if (user == null) {
			throw new NotFoundException("등록되지 않은 유저입니다.");
		}
		
		return new CustomUser(user.getSeq(), user.getId(), user.getPassword(), user.getEmail(), Arrays.asList("USER"));
	}

}
