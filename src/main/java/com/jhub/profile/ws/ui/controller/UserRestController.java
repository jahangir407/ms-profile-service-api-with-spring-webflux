package com.jhub.profile.ws.ui.controller;

import java.net.URI;

import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhub.profile.dto.UserDto;
import com.jhub.profile.ws.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
//@Profile("classic")
@Slf4j
public class UserRestController {

	private final MediaType mediaType = MediaType.APPLICATION_JSON;
	private final UserService userService;

	UserRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	Publisher<UserDto> getAllUser() {
		log.info("Retriving all user.");
		return this.userService.getAllUser();
	}

	@GetMapping("/{id}")
	Publisher<UserDto> getUserById(@PathVariable("id") String id) {
		log.info("Retriving User info for ID: "+id);
		return this.userService.getUserbyId(id);
	}

	@PostMapping
	Publisher<ResponseEntity<UserDto>> createUser(@RequestBody UserDto userDto) {
		log.info("Creating User "+ userDto.toString());
		return this.userService.createUser(userDto)
				.map(res -> ResponseEntity.created(URI.create("/user/" + res.getId())).contentType(mediaType).build());
	}

	@PutMapping("/{id}")
	Publisher<ResponseEntity<UserDto>> updateUserById(@PathVariable("id") String id, @RequestBody UserDto userDto) {
		log.info("Updating User "+ userDto.toString());
		return this.userService.updateUserById(id, userDto)
				.map(res -> ResponseEntity.ok().contentType(mediaType).build());

	}

	@DeleteMapping("/{id}")
	Publisher<UserDto> deleteUserById(@PathVariable("id") String id) {
		log.info("Creating User "+ id);
		return this.userService.deleteUserById(id);
	}

}
