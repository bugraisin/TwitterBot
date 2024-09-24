package com.twitterbot.services;

import com.twitterbot.entities.Post;
import com.twitterbot.entities.User;
import com.twitterbot.repositories.PostRepository;
import com.twitterbot.requests.PostCreateRequest;
import com.twitterbot.requests.PostUpdateRequest;
import com.twitterbot.responses.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if(userId.isPresent())
            list = postRepository.findByUserId(userId.get());
        list = postRepository.findAll();
        return list.stream().map(PostResponse::new).collect(Collectors.toList());
    }

    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user = userService.getOneUser(newPostRequest.getUserId());
        if(user == null)
            return null;
        Post post = new Post();
        post.setId(newPostRequest.getId());
        post.setTitle(newPostRequest.getTitle());
        post.setText(newPostRequest.getText());
        post.setUser(user);
        return postRepository.save(post);
    }

    public Post updateOnePost(Long postId, PostUpdateRequest newPostRequest) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()) {
            Post toUpdate = post.get();
            toUpdate.setTitle(newPostRequest.getTitle());
            toUpdate.setText(newPostRequest.getText());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deleteOnePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
