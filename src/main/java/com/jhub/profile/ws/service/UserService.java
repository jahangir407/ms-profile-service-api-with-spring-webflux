package com.jhub.profile.ws.service;

import com.jhub.profile.dto.UserDto;
import com.jhub.profile.io.entity.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

	public Flux<UserDto> getAllUser();

	Mono<UserDto> getUserbyId(String id);

	Mono<UserDto> updateUserById(String id, UserDto userDto);

	Mono<UserDto> deleteUserById(String id);

	Mono<UserDto> createUser(UserDto userDto);

}
