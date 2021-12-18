package com.jhub.profile.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jhub.profile.io.entity.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
