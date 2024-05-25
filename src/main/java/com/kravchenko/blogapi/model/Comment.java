package com.kravchenko.blogapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Comment {
    @Id
    private String id;
    @DBRef
    private Post post;
    private String content;
    private LocalDateTime publicationDate;
}
