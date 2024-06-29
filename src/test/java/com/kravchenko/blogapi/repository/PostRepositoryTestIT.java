package com.kravchenko.blogapi.repository;

import com.kravchenko.blogapi.model.Post;
import com.kravchenko.blogapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataMongoTest
class PostRepositoryTestIT {

    @Container
    @ServiceConnection
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    private PostRepository postRepository;

    @Test
    void connectionEstabled() {
        assertTrue(mongoDBContainer.isCreated());
        assertTrue(mongoDBContainer.isRunning());
    }

    @BeforeEach
    void setUp() {
        User user = new User("1", "brazuca", "brazuca.egor@gmail.com", "brazuca1234", "General user", null);
        User user2 = new User("2", "general", "general.egor@gmail.com", "general1234", "General user 2", null);


        Post post = new Post("1", user, "Title 1", "Content general 1", null, LocalDateTime.now());
        Post post2 = new Post("2", user2, "Title 2", "Content general 2", null, LocalDateTime.now());
        Post post3 = new Post("3", user, "Title 3", "Content general 3", null, LocalDateTime.now());

        List<Post> posts = new ArrayList<>(Arrays.asList(post, post2, post3));

        postRepository.saveAll(posts);
    }

    @Test
    void findById() {
        Optional<Post> post = postRepository.findById("1");
        assertTrue(post.isPresent());
    }

    @Test
    void findAll() {
        List<Post> posts = postRepository.findAll();
        assertEquals(3, posts.size());
    }

    @Test
    void shouldReturnPostByTitle() {
        Post post = postRepository.findByTitle("Title 3").orElseThrow();
        assertEquals("Title 3", post.getTitle());
    }

    @Test
    void shouldNotReturnPostWhenTitleIsNotFound() {
        Optional<Post> post = postRepository.findByTitle("Content general 102120");
        assertFalse(post.isPresent());
    }
}