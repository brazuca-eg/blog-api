package com.kravchenko.blogapi;

import com.kravchenko.blogapi.model.Post;
import com.kravchenko.blogapi.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApiApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runner(PostRepository postRepository) {
//        return args -> {
////            User user = new User();
////            user.setEmail("email");
//
//            Post post = new Post();
//            post.setContent("Contr");
//            post.setTitle("Title");
//           // post.setAuthor(user);
//
////            Post post2 = new Post();
////            post2.setContent("Contr2");
////            post.setTitle("Title 2");
////            post2.setAuthor(user);
//
//            postRepository.insert(post);
//
//        };
//    }

}
