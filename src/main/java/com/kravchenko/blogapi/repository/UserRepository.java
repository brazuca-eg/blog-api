package com.kravchenko.blogapi.repository;

import com.kravchenko.blogapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
