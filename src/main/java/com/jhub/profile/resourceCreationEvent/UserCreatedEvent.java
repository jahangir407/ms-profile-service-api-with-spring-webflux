package com.jhub.profile.resourceCreationEvent;

import org.springframework.context.ApplicationEvent;

import com.jhub.profile.io.entity.User;

public class UserCreatedEvent extends ApplicationEvent{

	private static final long serialVersionUID = -2057666890394209162L;

	public UserCreatedEvent(User source) {
		super(source);
	}


}
