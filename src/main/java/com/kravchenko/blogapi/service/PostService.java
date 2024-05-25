package com.kravchenko.blogapi.service;

import com.kravchenko.blogapi.model.Post;
import com.kravchenko.blogapi.model.User;
import com.kravchenko.blogapi.payload.UpdatePostPayload;
import com.kravchenko.blogapi.repository.PostRepository;
import com.kravchenko.blogapi.repository.UserRepository;
import com.kravchenko.blogapi.repository.custom.CustomPostRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class PostService extends AbstractService<Post, String, PostRepository> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomPostRepositoryImpl customPostRepository;
    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Post createPost(Post post) {
        User author = userRepository.findById(post.getAuthor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        post.setAuthor(author);
        post = create(post);

        author.getPosts().add(post);
        userRepository.save(author);

        return post;
    }

    @Transactional
    public void updatePost(String postId, UpdatePostPayload updatePostPayload) throws Exception {
        Post existingPost = getById(postId);
        if (Objects.isNull(existingPost)) {
            throw new Exception("Post not found");
        }
        customPostRepository.updatePostTitleAndContent(postId, updatePostPayload.getTitle(),
                updatePostPayload.getContent());
    }

    public Page<Post> findByAuthor(String authorId, int pageNumber, int pageSize) {
        User user = userRepository.findById(authorId).orElseThrow();

        return postRepository.findByAuthor(user, PageRequest.of(pageNumber, pageSize));
    }
}
