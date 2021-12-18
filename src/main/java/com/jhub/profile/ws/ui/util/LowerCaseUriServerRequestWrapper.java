package com.jhub.profile.ws.ui.util;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpCookie;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import org.springframework.web.util.UriBuilder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class LowerCaseUriServerRequestWrapper extends ServerRequestWrapper {

	public LowerCaseUriServerRequestWrapper(ServerRequest delegate) {
		super(delegate);
	}

	@Override
	public URI uri() {
		return URI.create(super.uri().toString().toLowerCase());
	}

	@Override
	public String path() {
		return uri().getRawPath();
	}

	@Override
	public PathContainer pathContainer() {
		return PathContainer.parsePath(path());
	}

}
