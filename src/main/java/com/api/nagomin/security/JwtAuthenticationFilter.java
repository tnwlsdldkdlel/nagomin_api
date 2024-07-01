package com.api.nagomin.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.nagomin.util.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;

	private String getJWTtoken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// JWT 토큰 추출
		String token = getJWTtoken((HttpServletRequest) request);

		// 토큰 유효성 검사
		jwtUtils.validateToken(token);
		Authentication authentication = jwtUtils.getAuthentication(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		// 체크 못하도록
		if (path.startsWith("/v1/user/join")) {
			return true;
		}
		
		if (path.startsWith("/v1/user/login")) {
			return true;
		}
		
		if (path.startsWith("/v1/user/interest")) {
			return true;
		}

		return false;
	}
}