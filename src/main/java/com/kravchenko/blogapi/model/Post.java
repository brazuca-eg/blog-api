package com.kravchenko.blogapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @JsonCreator
    public Post(
            @JsonProperty("id") String id,
            @JsonProperty("author") User author,
            @JsonProperty("title") String title,
            @JsonProperty("content") String content,
            @JsonProperty("comments") List<Comment> comments,
            @JsonProperty("publicationDate") LocalDateTime publicationDate) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
