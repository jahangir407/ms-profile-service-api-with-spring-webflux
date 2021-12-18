package com.jhub.profile.io.entity;


import org.springframework.data.mongodb.core.mapping.Document;

import com.googlecode.jmapper.annotations.JGlobalMap;

import lombok.Getter;
import lombok.Setter;

@JGlobalMap
@Document
@Getter
@Setter
public class User extends BaseEntity {

	private String firstName;
	private String lastName;
	private String email;

}
