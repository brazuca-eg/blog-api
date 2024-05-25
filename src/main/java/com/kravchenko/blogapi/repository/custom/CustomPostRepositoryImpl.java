package com.kravchenko.blogapi.repository.custom;

import com.kravchenko.blogapi.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class CustomPostRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void updatePostTitleAndContent(String postId, String title, String content) {
        Query query = new Query(Criteria.where("_id").is(postId));
        Update update = new Update().set("title", title).set("content", content);
        mongoTemplate.updateFirst(query, update, Post.class);
    }
}
