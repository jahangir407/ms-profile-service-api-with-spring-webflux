package com.jhub.profile.io.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Profile extends BaseEntity{
	
	private String firstName;
	private String lastName;
	private String email;

}
