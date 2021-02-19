package com.jhub.profile.ws.ui.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.jhub.profile.ws.ui.handler.UserHandler;
import com.jhub.profile.ws.ui.util.CaseInsensitiveRequestPredicate;

import static org.springframework.web.reactive.function.server.RequestPredicate.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserEndpointConfiguration {

	@Bean
	RouterFunction<ServerResponse> routes(UserHandler handler) {

		return null;
	}

	private static RequestPredicate i(RequestPredicate target) {
		return new CaseInsensitiveRequestPredicate(target);
	}

}
