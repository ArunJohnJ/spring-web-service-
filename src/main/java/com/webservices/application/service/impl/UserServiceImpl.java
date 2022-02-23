package com.webservices.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.webservices.application.entity.UserEntity;
import com.webservices.application.repository.UserRepository;
import com.webservices.application.service.UserService;
import com.webservices.application.shared.Utils;
import com.webservices.application.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Utils utils;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);

		// since we added encryptedPassword and UserId as non-null in UserEntity
		// definition, therefore we have to set the value before trying to save to DB

		String userIdGenerated = utils.generateUserId(30);
		userEntity.setUserId(userIdGenerated);
		String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
		userEntity.setEncryptedPassword(encryptedPassword);
		UserEntity savedUser = userRepository.save(userEntity);
		UserDto savedUserDto = new UserDto();
		BeanUtils.copyProperties(savedUser, savedUserDto);
		return savedUserDto;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email); // this exception comes from Spring
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		UserDto response = new UserDto();
		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		BeanUtils.copyProperties(userEntity, response);
		return response;
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnedUsers = new ArrayList<>();
		if (page > 0)
			page = page - 1;// so that in query string, we can give page 1 itself instead of 0(by default
							// the pagination logic returns 0 for page, we can't change that, we can just
							// change how the user will query)

		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<UserEntity> pageableUsers = userRepository.findAll(pageableRequest);
		System.out.println("total elements :"+pageableUsers.getTotalElements());
		System.out.println("total pages :"+pageableUsers.getTotalPages());//totalelements/size = totalpages
		System.out.println(pageableUsers.getTotalElements()/pageableUsers.getSize());
		List<UserEntity> users = pageableUsers.getContent();
		for (UserEntity user : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			returnedUsers.add(userDto);
		}
		return returnedUsers;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserDto returnedUser = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		BeanUtils.copyProperties(userEntity, returnedUser);
		return returnedUser;
	}

}
