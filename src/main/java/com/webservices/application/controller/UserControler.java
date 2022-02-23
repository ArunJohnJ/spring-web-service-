package com.webservices.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webservices.application.model.ui.request.UserRequest;
import com.webservices.application.model.ui.response.UserResponse;
import com.webservices.application.service.UserService;
import com.webservices.application.shared.dto.UserDto;

@RestController
@RequestMapping("users")
public class UserControler {

	@Autowired
	private UserService userService;
	
	@GetMapping(path = "/{userId}")
	public UserResponse getUser(@PathVariable String userId) {
		UserResponse response = new UserResponse();
		UserDto user = userService.getUserByUserId(userId);
		BeanUtils.copyProperties(user, response);
		return response;
	}

	@PostMapping
	public UserResponse createUser(@RequestBody UserRequest user) {
		UserResponse response = new UserResponse();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, response);
		return response;
	}
	@GetMapping(produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<UserResponse> getUsers(@RequestParam(value = "page",defaultValue = "0") int page, 
			@RequestParam(value = "limit",defaultValue = "25") int limit) {
		List<UserResponse> returnedUsers = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page,limit);
		
		for(UserDto user:users) {
			UserResponse userResponse = new UserResponse();
			BeanUtils.copyProperties(user, userResponse);
			returnedUsers.add(userResponse);
		}
		return returnedUsers;
	}

}
