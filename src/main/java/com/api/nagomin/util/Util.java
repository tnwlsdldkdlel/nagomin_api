package com.api.nagomin.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;

import com.api.nagomin.security.CustomUser;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Util {
	
	public static int createdAt() {
		return (int) (new Date().getTime() / 1000);
	}
	
	public static CustomUser getLoginUserInfo() {
		return (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public static Map<String, Object> oneDataToMap(String key, Object value) {
		Map<String, Object> result = new HashMap<>();
		result.put(key, value);
		
		return result;
	}
}