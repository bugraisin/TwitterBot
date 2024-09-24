package com.twitterbot.responses;

import com.twitterbot.entities.Post;
import lombok.Data;

@Data
public class PostResponse {

    long id;
    long userId;
    String userName;
    String title;
    String text;

    public PostResponse(Post entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getUserName();
        this.title = entity.getTitle();
        this.text = entity.getText();
    }
}
