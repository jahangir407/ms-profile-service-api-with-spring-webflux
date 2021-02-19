package com.jhub.profile.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import com.googlecode.jmapper.JMapper;
import com.jhub.profile.dto.UserDto;
import com.jhub.profile.io.entity.User;
import com.jhub.profile.repository.UserRepository;
import com.jhub.profile.ws.service.impl.UserServiceImpl;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Log4j2
@DataMongoTest
@Import(value = { UserServiceImpl.class })
public class UserServiceTest {

	private final UserRepository userRepository;
	private final UserServiceImpl userService;

	/**
	 * @param userRepository
	 * @param userService
	 */
	public UserServiceTest(@Autowired UserRepository userRepository, @Autowired UserServiceImpl userService) {
		super();
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@Test
	public void getAllUser() {
		log.info("-----Test 1: getAll()------");

		JMapper<UserDto, User> userToUserDtoMapper = new JMapper<UserDto, User>(UserDto.class, User.class);

		List<User> users = Arrays.asList(
				new User(UUID.randomUUID().toString(), "Mr", "X", "x@example.com", LocalDateTime.now(),
						LocalDateTime.now(), LocalDateTime.now()),
				new User(UUID.randomUUID().toString(), "Mr", "Y", "y@example.com", LocalDateTime.now(),
						LocalDateTime.now(), LocalDateTime.now()),
				new User(UUID.randomUUID().toString(), "Mr", "Z", "z@example.com", LocalDateTime.now(),
						LocalDateTime.now(), LocalDateTime.now())

		);

		Flux<User> saved = userRepository.saveAll(Flux.fromIterable(users)).log("---userRepository.saveAll()----");

		Flux<UserDto> composite = saved.filter(x -> {
			log.info("req.x----" + x.toString());
			return true;
		}).thenMany(this.userService.getAllUser());
		log.info("-------invoking StepVerifier------------------");

		Predicate<UserDto> match = u -> saved.any(i -> {
			log.info("res.u----" + u.toString());
			return i.getEmail().equals(u.getEmail());
		}).block();

		StepVerifier.create(composite).expectNextMatches(match).expectNextMatches(match).expectNextMatches(match)
				.verifyComplete();

	}

	@Test
	public void save() {

		log.info("-----Test 2: save()------");

		UserDto user = new UserDto(UUID.randomUUID().toString(), "Mr", "Snow", "z@example.com", LocalDateTime.now(),
				LocalDateTime.now(), LocalDateTime.now());
		Mono<UserDto> savedUser = userService.createUser(user);

		StepVerifier.create(savedUser).expectNextMatches(s -> s.getId().equals(user.getId())).verifyComplete();
	}

	@Test
	public void delete() {

		log.info("-----Test 3: delete()------");

		UserDto user = new UserDto(UUID.randomUUID().toString(), "John", "Wick", "wick@example.com",
				LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
		Mono<UserDto> deletedUser = this.userService.createUser(user)
				.flatMap(u -> this.userService.deleteUserById(u.getId()));
		StepVerifier.create(deletedUser).expectNextMatches(r -> r.getId().equals(user.getId())).verifyComplete();

	}

	@Test
	public void update() {

		log.info("-----Test 4: update()------");

		UserDto user = new UserDto(UUID.randomUUID().toString(), "Alexandra", "Daddario", "alexandra@example.com",
				LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());

		Mono<UserDto> updatedUser = this.userService.createUser(user).flatMap(res -> {
			res.setEmail("daddario@example.com");
			return this.userService.updateUserById(res.getId(),res);
		});

		StepVerifier.create(updatedUser).expectNextMatches(res -> res.getEmail().equals("daddario@example.com"))
				.verifyComplete();

	}

	@Test
	public void getById() {
		
		log.info("-----Test 5: getById()------");

		UserDto user = new UserDto(UUID.randomUUID().toString(), "Emma", "Stone", "emma@example.com",
				LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());

		Mono<UserDto> retrivedUser = this.userService.createUser(user)
				.thenMany(this.userService.getUserbyId(user.getId())).next();

		StepVerifier.create(retrivedUser).expectNextMatches(res -> res.getId().equals(user.getId())).verifyComplete();

	}

}
