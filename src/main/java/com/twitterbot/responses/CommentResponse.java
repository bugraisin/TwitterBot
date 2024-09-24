package com.twitterbot.responses;

import com.twitterbot.entities.Comment;

public class CommentResponse {

    long id;
    long postId;
    long userId;
    String text;
    String username;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.userId = comment.getUser().getId();
        this.text = comment.getText();
        this.username = comment.getUser().getUserName();
    }
}
