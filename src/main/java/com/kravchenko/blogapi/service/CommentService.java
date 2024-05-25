package com.kravchenko.blogapi.service;

import com.kravchenko.blogapi.model.Comment;
import com.kravchenko.blogapi.model.Post;
import com.kravchenko.blogapi.repository.CommentRepository;
import com.kravchenko.blogapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService extends AbstractService<Comment, String, CommentRepository> {

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Comment createComment(Comment comment) {
        Post post = postRepository.findById(comment.getPost().getId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        comment.setPost(post);
        comment = create(comment);

        post.getComments().add(comment);
        postRepository.save(post);

        return comment;
    }

}
