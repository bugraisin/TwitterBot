package com.twitterbot.services;

import com.twitterbot.entities.Comment;
import com.twitterbot.entities.Post;
import com.twitterbot.entities.User;
import com.twitterbot.repository.CommentRepository;
import com.twitterbot.requests.CommentCreateRequest;
import com.twitterbot.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllComments(Optional<Long> userId) {
        if(userId.isPresent())
            return commentRepository.findByUserId(userId.get());
        return commentRepository.findAll();
    }

    public Comment getOneComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest newCommentRequest) {
        User user = userService.getOneUser(newCommentRequest.getUserId());
        Post post = postService.getOnePostById(newCommentRequest.getPostId());
        if(user == null || post == null)
            return null;
        Comment comment = new Comment();
        comment.setId(newCommentRequest.getId());
        comment.setUser(user);
        comment.setPost(post);
        comment.setText(newCommentRequest.getText());
        return commentRepository.save(comment);
    }

    public Comment updateOneComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()) {
            Comment updatedComment = comment.get();
            updatedComment.setText(commentUpdateRequest.getText());
            commentRepository.save(updatedComment);
            return updatedComment;
        }
        return null;
    }

    public void deleteOneComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}