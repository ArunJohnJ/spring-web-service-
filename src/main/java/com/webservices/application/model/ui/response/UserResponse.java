package com.webservices.application.model.ui.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class UserResponse {

	private String userId;
	private String firstName;	
	private String lastName;
	private String email;
	//we don't return password in the response
}
