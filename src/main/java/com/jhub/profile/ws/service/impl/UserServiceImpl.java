package com.jhub.profile.ws.service.impl;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.googlecode.jmapper.JMapper;
import com.jhub.profile.dto.UserDto;
import com.jhub.profile.io.entity.User;
import com.jhub.profile.repository.UserRepository;
import com.jhub.profile.resourceCreationEvent.UserCreatedEvent;
import com.jhub.profile.ws.service.UserService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

	private final ApplicationEventPublisher publisher;
	private final UserRepository userRepository;
	private final JMapper<UserDto, User> userToUserDtoMapper;
	private final JMapper<User, UserDto> userDtoToUserMapper;

	public UserServiceImpl(ApplicationEventPublisher publisher, UserRepository userRepository) {
		this.userRepository = userRepository;
		this.publisher = publisher;
		this.userToUserDtoMapper = new JMapper<>(UserDto.class, User.class);
		this.userDtoToUserMapper = new JMapper<>(User.class, UserDto.class);
	}

	@Override
	public Flux<UserDto> getAllUser() {
		log.debug("------User getAll() is accessed------");
		return this.userRepository.findAll().map(u -> userToUserDtoMapper.getDestination(u));
	}

	@Override
	public Mono<UserDto> getUserbyId(String id) {
		return this.userRepository.findById(id).map(u -> userToUserDtoMapper.getDestination(u));
	}

	@Override
	public Mono<UserDto> updateUserById(String id, UserDto userDto) {
		userDto.setUpdatedAt(LocalDateTime.now());
		return this.userRepository
				.findById(id)
				.map(u -> {
					u.setFirstName(userDto.getFirstName());
					u.setLastName(userDto.getLastName());
					u.setEmail(userDto.getEmail());
					u.setUpdatedAt(LocalDateTime.now());
					return u;
					})
				.flatMap(this.userRepository::save)
				.map(u-> userToUserDtoMapper.getDestination(u));
	}

	@Override
	public Mono<UserDto> deleteUserById(String id) {
		return this.userRepository
				.findById(id)
				.flatMap(u-> this.userRepository
										.deleteById(u.getId())
										.thenReturn(u)
										.map(r-> userToUserDtoMapper.getDestination(r)));
	}

	@Override
	public Mono<UserDto> createUser(UserDto userDto) {
		userDto.setId(null);
		userDto.setCreatedAt(LocalDateTime.now());
		userDto.setUpdatedAt(LocalDateTime.now());
		return this.userRepository
				.save(userDtoToUserMapper.getDestination(userDto))
				.doOnSuccess(u-> this.publisher.publishEvent(new UserCreatedEvent(u)))
				.map(u-> userToUserDtoMapper.getDestination(u));
	}

}
