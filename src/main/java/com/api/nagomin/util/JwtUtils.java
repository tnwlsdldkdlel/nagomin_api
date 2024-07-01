package com.api.nagomin.util;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.api.nagomin.exception.ExpiredDataException;
import com.api.nagomin.exception.InvalidDataException;
import com.api.nagomin.security.CustomUserDetailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtUtils {
	private final Key key;
	private final CustomUserDetailService customUserDetailService;

	public JwtUtils(@Value("${value.jwt.key}") String secretKey, CustomUserDetailService customUserDetailService) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.customUserDetailService = customUserDetailService;
	}

	public String generateToken(Map<String, Object> map) {
		// 권한 가져오기
//		String authorities = authentication.getAuthorities().stream()
//				.map(GrantedAuthority::getAuthority)
//				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		// 하루로 설정.
		Date expiration = new Date(now + (24 * 60 * 60 * 1000));
		String jwt = Jwts.builder()
				.claim("info", map)
				.setExpiration(expiration)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();

		return jwt;
	}

	public Authentication getAuthentication(String jwt) {
		Claims claims = parseClaims(jwt);
		Map<String, Object> infoMap = (Map<String, Object>) claims.get("info");
		UserDetails userDetails = customUserDetailService.loadUserByUsername(infoMap.get("id").toString());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			throw new InvalidDataException("유효하지않은 토큰.");
		} catch (ExpiredJwtException e) {
			throw new ExpiredDataException("만료된 토큰.");
		} catch (Exception e) {
			throw new InvalidDataException("유효하지않은 토큰.");
		}
	}

	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(accessToken)
					.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

}
