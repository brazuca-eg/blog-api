package com.kravchenko.blogapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Post {
    @Id
    private String id;
    @DBRef
    private User author;
    private String title;
    private String content;
    @DBRef
    private List<Comment> comments;
    private LocalDateTime publicationDate;
}
