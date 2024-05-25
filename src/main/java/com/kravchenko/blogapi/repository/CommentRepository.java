package com.kravchenko.blogapi.repository;

import com.kravchenko.blogapi.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {

}
