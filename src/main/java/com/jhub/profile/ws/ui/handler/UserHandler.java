package com.jhub.profile.ws.ui.handler;

import java.net.URI;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.jhub.profile.dto.UserDto;
import com.jhub.profile.ws.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

	private final UserService userService;

	public UserHandler(@Autowired UserService userService) {

		this.userService = userService;
	}

	Mono<ServerResponse> getUserById(ServerRequest r) {
		return defaultReadResponse(this.userService.getUserbyId(id(r)));
	}

	Mono<ServerResponse> getAllUser(ServerResponse r) {
		return defaultReadResponse(this.userService.getAllUser());
	}

	Mono<ServerResponse> deleteUserById(ServerRequest r) {
		return defaultReadResponse(this.userService.deleteUserById(id(r)));
	}

	Mono<ServerResponse> updateUserById(ServerRequest r) {
		Flux<UserDto> updatedUser = r.bodyToFlux(UserDto.class).flatMap(u -> this.userService.updateUserById(id(r), u));
		return defaultReadResponse(updatedUser);
	}

	Mono<ServerResponse> createUser(ServerRequest r) {
		Flux<UserDto> createdUser = r.bodyToFlux(UserDto.class).flatMap(u -> this.userService.createUser(u));
		return defaultWriteResponse(createdUser);
	}

	private static Mono<ServerResponse> defaultWriteResponse(Publisher<UserDto> userDto) {

		return Mono.from(userDto).flatMap(u -> ServerResponse.created(URI.create("/user/" + u.getId()))
				.contentType(MediaType.APPLICATION_JSON).build());

	}

	private static Mono<ServerResponse> defaultReadResponse(Publisher<UserDto> userDto) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userDto, UserDto.class);

	}

	private static String id(ServerRequest r) {
		return r.pathVariable("id");
	}

}
