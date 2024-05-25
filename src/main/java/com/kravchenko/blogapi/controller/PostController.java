package com.kravchenko.blogapi.controller;

import com.kravchenko.blogapi.dto.ErrorResponseDto;
import com.kravchenko.blogapi.model.Post;
import com.kravchenko.blogapi.payload.UpdatePostPayload;
import com.kravchenko.blogapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CRUD REST APIs for Posts")
@RestController
@RequestMapping("api/v1/posts")
public class PostController implements GeneralCrudController<Post> {

    @Autowired
    private PostService postService;

    @Operation(
            summary = "Create Post REST API method",
            description = "REST API to create new post"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        try {
            return ResponseEntity.ok(postService.createPost(post));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Get Posts REST API method", description = "REST API to GET all posts")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

    @Operation(summary = "Get Post by Id REST API method", description = "REST API to GET post by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable String id) {
        Post post = postService.getById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update Post content by Id REST API method", description = "REST API to update post by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP 204 Success No Content"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody UpdatePostPayload payload) throws Exception {
        postService.updatePost(id, payload);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Posts by author REST API method", description = "REST API to GET posts by author")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/findByAuthor/{authorId}")
    public ResponseEntity<Page<Post>> findByAuthor(@PathVariable String authorId) {
        return ResponseEntity.ok(postService.findByAuthor(authorId, 1, 3));
    }
}
