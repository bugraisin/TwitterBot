package com.twitterbot.services;

import com.twitterbot.entities.Like;
import com.twitterbot.entities.Post;
import com.twitterbot.entities.User;
import com.twitterbot.repositories.LikeRepository;
import com.twitterbot.requests.LikeCreateRequest;
import com.twitterbot.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    public LikeService(UserService userService, PostService postService, LikeRepository likeRepository) {
        this.userService = userService;
        this.postService = postService;
        this.likeRepository = likeRepository;
    }

    public List<LikeResponse> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()) {
            list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if(userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        } else if(postId.isPresent()) {
            list = likeRepository.findByPostId(postId.get());
        } else {
            list = likeRepository.findAll();
        }
        return list.stream().map(LikeResponse::new).collect(Collectors.toList());
    }

    public Like getOneLike(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest likeCreateRequest) {
        User user = userService.getOneUser(likeCreateRequest.getUserId());
        Post post = postService.getOnePostById(likeCreateRequest.getPostId());
        if(user == null || post == null) {
            return null;
        }
        Like newLike = new Like();
        newLike.setUser(user);
        newLike.setPost(post);
        newLike.setId(likeCreateRequest.getId());
        return likeRepository.save(newLike);
    }

    public void deleteOneLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
