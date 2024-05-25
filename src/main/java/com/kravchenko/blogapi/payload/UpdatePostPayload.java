package com.kravchenko.blogapi.payload;

import lombok.Data;

@Data
public class UpdatePostPayload {
    private String title;
    private String content;
}
