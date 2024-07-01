package com.api.nagomin.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class CustomUser extends User {
	private Long seq;
	private String id;
	private String password;
	private String email;
	private List<String> roleNames  = new ArrayList<>();

	public CustomUser(Long seq, String id, String password, String email, List<String> roleNames) {
		super(id, password, roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_" + str)).collect(Collectors.toList()));
		this.seq = seq;
		this.id = id;
		this.password = password;
		this.email = email;
		this.roleNames = roleNames;
	}
	
	public Map<String, Object> getClaims() {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("seq", seq);
		dataMap.put("id", id);
		dataMap.put("email", email);
		dataMap.put("auth", roleNames.get(0));

		return dataMap;
	}
	
}
