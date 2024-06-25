package com.api.nagomin.service;

import com.api.nagomin.dto.JoinDto;
import com.api.nagomin.entity.User;

public interface UserService {
	
	public User save(JoinDto joinDto);

}
