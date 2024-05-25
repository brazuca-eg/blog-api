package com.kravchenko.blogapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private String id;
    private String postId;
    private String content;
    private LocalDateTime publicationDate;
}
