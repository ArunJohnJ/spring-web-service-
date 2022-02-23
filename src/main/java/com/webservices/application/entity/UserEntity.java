package com.webservices.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "users")
@Getter
@Setter
@ToString
public class UserEntity {

	@Id
	@GeneratedValue
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private long id;

	@Column(nullable = false)
	private String userId;
	@Column(nullable = false, length = 30)
	private String firstName;
	@Column(nullable = false, length = 30)
	private String lastName;
	@Column(nullable = false)
	private String encryptedPassword;
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	private String emailVerificationToken;
	@Column(nullable = false)
	private boolean emailVerificationStatus = false;
}
