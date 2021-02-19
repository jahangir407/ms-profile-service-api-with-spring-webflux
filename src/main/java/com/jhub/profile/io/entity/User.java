package com.jhub.profile.io.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.googlecode.jmapper.annotations.JGlobalMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JGlobalMap
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	private String id = UUID.randomUUID().toString();

	private String firstName;
	private String lastName;
	private String email;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

}
