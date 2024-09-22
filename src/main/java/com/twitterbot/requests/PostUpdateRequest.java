package com.twitterbot.requests;

import lombok.Data;

@Data
public class PostUpdateRequest {
    String text;
    String title;
}
