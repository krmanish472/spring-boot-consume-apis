package com.techxtor.consumeapi.controller;

import com.techxtor.consumeapi.model.Post;
import com.techxtor.consumeapi.service.WebClientConsumeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class WebClientConsumeApiController {
    @Autowired
    private WebClientConsumeApiService consumeApiService;

    // http://localhost:8080/posts
    // http://localhost:8080/posts?userId=1
    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam(value = "userId", required = false) Integer id) {
        Post[] posts = id == null ? consumeApiService.getAllPosts() : consumeApiService.getPostsByUserId(id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // http://localhost:8080/posts/1
    @GetMapping("/{postId}")
    public Post getPostByPostId(@PathVariable int postId) {
        return consumeApiService.getPostByPostId(postId);
    }

    // http://localhost:8080/posts
    @PostMapping()
    public Post addPosts(@RequestBody Post post) {
        return consumeApiService.addPost(post);
    }

}
