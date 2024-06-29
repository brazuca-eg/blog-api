package com.kravchenko.blogapi.controller;

import com.kravchenko.blogapi.model.Post;
import com.kravchenko.blogapi.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerTestIT {

    @Container
    @ServiceConnection
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeAll
    static void setUp(@Autowired MongoTemplate mongoTemplate) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ClassPathResource postsResource = new ClassPathResource("posts.json");
        try (InputStream inputStream = postsResource.getInputStream()) {
            List<Post> posts = mapper.readValue(inputStream, new TypeReference<List<Post>>() {
            });
            mongoTemplate.insertAll(posts);
        }
        ClassPathResource usersResource = new ClassPathResource("users.json");
        try (InputStream inputStream = usersResource.getInputStream()) {
            List<User> users = mapper.readValue(inputStream, new TypeReference<List<User>>() {
            });
            mongoTemplate.insertAll(users);
        }
    }

    @Test
    void getAll() {
        //WHEN
        Post[] posts = restTemplate.getForObject("/api/v1/posts", Post[].class);

        //THEN
        assertEquals(2, posts.length);
    }

    @Test
    void shouldFindPostWhenValidPostID() {
        //WHEN
        ResponseEntity<Post> response = restTemplate.exchange("/api/v1/posts/1", HttpMethod.GET, null, Post.class);

        //THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldThrowNotFoundWhenInvalidPostID() {
        //WHEN
        ResponseEntity<Post> response = restTemplate.exchange("/api/v1/posts/199", HttpMethod.GET, null, Post.class);

        //THEN
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Rollback
    void shouldCreateNewPostWhenPostIsValid() {
        //GIVEN
        User user = new User();
        user.setId("22");
        Post post = new Post();
        post.setAuthor(user);
        post.setId("post-99");
        post.setTitle("99 Title");
        post.setContent("99 Content");

        //WHEN
        ResponseEntity<Post> response = restTemplate.exchange("/api/v1/posts", HttpMethod.POST, new HttpEntity<>(post), Post.class);

        //THEN
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo("post-99");
        assertThat(response.getBody().getTitle()).isEqualTo("99 Title");
        assertThat(response.getBody().getContent()).isEqualTo("99 Content");
    }

    @Test
    void shouldNotCreateNewPostWhenValidationFails() {
        User user = new User();
        user.setId("Non existing id");
        Post post = new Post();
        post.setId("400");
        post.setAuthor(user);

        ResponseEntity<Post> response = restTemplate.exchange("/api/v1/posts", HttpMethod.POST, new HttpEntity<>(post), Post.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}