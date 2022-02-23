package com.webservices.application.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.webservices.application.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto userDto);

	UserDto getUser(String email);

	UserDto getUserByUserId(String userId);
	
	List<UserDto> getUsers(int page, int limit);
}
