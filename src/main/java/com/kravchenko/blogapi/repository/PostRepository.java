package com.kravchenko.blogapi.repository;

import com.kravchenko.blogapi.model.Post;
import com.kravchenko.blogapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findByAuthor(User author, Pageable pageable);

    Optional<Post> findByTitle(String title);
}
