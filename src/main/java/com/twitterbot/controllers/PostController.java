package com.twitterbot.controllers;

import com.twitterbot.entities.Post;
import com.twitterbot.entities.User;
import com.twitterbot.requests.PostCreateRequest;
import com.twitterbot.requests.PostUpdateRequest;
import com.twitterbot.services.PostService;
import com.twitterbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts(@RequestParam Optional<Long> userId) {
        return postService.getAllPosts(userId);
    }

    @GetMapping("/{postId}")
    public Post getOnePost(@PathVariable long postId) {
        return postService.getOnePostById(postId);
    }

    @PostMapping
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) {
        return postService.createOnePost(newPostRequest);
    }

    @PutMapping("/{postId}")
    public Post updateOnePost(@PathVariable long postId, @RequestBody PostUpdateRequest newPostRequest) {
        return postService.updateOnePost(postId, newPostRequest);
    }

    @DeleteMapping("/{postId}")
    public void deleteOnePost(@PathVariable long postId) {
        postService.deleteOnePost(postId);
    }

}
