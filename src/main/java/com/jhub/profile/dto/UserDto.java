package com.jhub.profile.dto;

import java.time.LocalDateTime;

import com.googlecode.jmapper.annotations.JGlobalMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JGlobalMap
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private String id;

	private String firstName;
	private String lastName;
	private String email;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

}
