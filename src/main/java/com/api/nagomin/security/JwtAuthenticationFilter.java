package com.api.nagomin.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.nagomin.dto.ResultDto;
import com.api.nagomin.util.JwtUtils;
import com.api.nagomin.util.ResponseCode;

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

	private boolean isLogoutRequest(HttpServletRequest request) {
		return "/v1/user/logout".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod());
	}

	private void performLogout(HttpServletResponse response, String token) throws IOException {
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		PrintWriter writer = response.getWriter();
		
		if (token != null) {
			jwtUtils.blacklistToken(token);
            writer.println(ResultDto.success(ResponseCode.SUCCESS.getMessage()).toJsonString());
		} else {
			writer.println(ResultDto.success(ResponseCode.BAD_REQUEST.getMessage()).toJsonString());
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// JWT 토큰 추출
		String token = getJWTtoken((HttpServletRequest) request);
		
		if (!isLogoutRequest(request)) {
			// 토큰 유효성 검사
			jwtUtils.validateToken(token);
			Authentication authentication = jwtUtils.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			performLogout(response, token);
			return;
		}
		
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