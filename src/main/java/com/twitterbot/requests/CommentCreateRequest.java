package com.twitterbot.requests;

import lombok.Data;

@Data
public class CommentCreateRequest {

    Long id;
    Long postId;
    Long userId;
    public String text;
}
