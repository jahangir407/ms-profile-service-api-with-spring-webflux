package com.jhub.profile.io.entity;

import java.time.LocalDateTime;
import java.util.UUID;


import org.springframework.data.annotation.Id;



public abstract class BaseEntity {
	
	@Id
	private UUID id;
	private LocalDateTime createdAt; 
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

}
