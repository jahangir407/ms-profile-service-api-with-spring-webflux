package com.jhub.profile.dataseeder;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.jhub.profile.io.entity.User;
import com.jhub.profile.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
@Component
@Profile(value = { "demo" })
public class SampleDataInitializer implements ApplicationListener<ApplicationReadyEvent>{
	
	private final UserRepository userRepository;
	
	public SampleDataInitializer(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		log.info("--------------testing loging-----------------");
		
		userRepository
			.deleteAll()
			.thenMany(
				Flux
					.just("John","Tom","Snow", "William", "Tompson")
					.map(name -> new  User(UUID.randomUUID().toString(), "Mr", name, name+"@example.com", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().minusMonths(5)))
					.flatMap(userRepository::save)
				
			)
			.thenMany(userRepository.findAll())
			.subscribe(profile-> log.info("saving...."+ profile.toString()));
		
	}

}
