package com.api.nagomin.util;

import java.util.Date;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Util {
	
	public static int createdAt() {
		return (int) (new Date().getTime() / 1000);
	}
	
}