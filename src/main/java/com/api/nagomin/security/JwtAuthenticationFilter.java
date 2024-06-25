package com.api.nagomin.security;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.nagomin.dto.ResultDto;
import com.api.nagomin.util.ResponseCode;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

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
		if (token == null || !jwtTokenProvider.validateToken(token)) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			OutputStream outputStream = response.getOutputStream();
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
			printWriter.println(ResultDto.fail(ResponseCode.BAD_TOKEN, null).toJsonString());
			printWriter.flush();
			printWriter.close();
		} else {
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
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
		
		if (path.startsWith("/v1/user/interest")) {
			return true;
		}

		return false;
	}
}