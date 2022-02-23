package com.webservices.application.model.ui.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserLoginRequest {
	private String email;
	private String password;
}
