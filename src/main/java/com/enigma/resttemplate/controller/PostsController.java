package com.enigma.resttemplate.controller;

import com.enigma.resttemplate.entity.Posts;
import com.enigma.resttemplate.model.response.GetAllDataPostsResponse;
import com.enigma.resttemplate.model.response.PostsResponse;
import com.enigma.resttemplate.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostsController {
    private final PostsService postsService;

    @GetMapping
    public ResponseEntity<Posts[]> getAllPosts(){
       return postsService.getAllPost();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPostsById(@PathVariable Long id) {
       return postsService.getPostsById(id);
    }

    @GetMapping(value = "/comment{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPostsCommentByPostsId(@RequestParam Long postId) {
       return postsService.getCommentsByPostId(postId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostsResponse> createNewPosts(@RequestBody Posts posts){
        return postsService.creatPosts(posts);
    }

    @GetMapping("/getAllData")
    public ResponseEntity<GetAllDataPostsResponse> getAllData() {
        return postsService.getAllData();
    }
}
