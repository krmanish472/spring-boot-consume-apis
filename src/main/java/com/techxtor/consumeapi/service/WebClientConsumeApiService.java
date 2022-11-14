package com.techxtor.consumeapi.service;

import com.techxtor.consumeapi.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientConsumeApiService {
    private WebClient jsonplaceholderWebClient;

    @Autowired
    public WebClientConsumeApiService(WebClient jsonplaceholderWebClient) {
        this.jsonplaceholderWebClient = jsonplaceholderWebClient;
    }

    public Post[] getAllPosts() {
        return jsonplaceholderWebClient
                .get()
                .uri("/posts")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Post[].class)
                .block();
    }

    public Post[] getPostsByUserId(int id) {
        return jsonplaceholderWebClient
                .get()
                .uri("/posts?userId={id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Post[].class)
                .block();
    }

    public Post getPostByPostId(int postId) {
        return jsonplaceholderWebClient
                .get()
                .uri("/posts/{id}", postId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Post.class)
                .block();
    }

    public Post addPost(Post post) {
        return jsonplaceholderWebClient
                .post()
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(post), Post.class)
                .retrieve()
                .bodyToMono(Post.class)
                .block();
    }
}
