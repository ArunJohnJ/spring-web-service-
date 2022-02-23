package com.webservices.application.shared.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto implements Serializable {
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 7021951915055652200L;
	private long id; // this value will be from the database which will be auto-incremented
	private String userId; // we will return this alpha numeric number back to the user in the UI
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String encryptedPassword;
	private String emailVerficationToken;
	private boolean emailVerificationStatus = false;// adding default value as false

}
