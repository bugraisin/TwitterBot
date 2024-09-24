package com.twitterbot.repositories;

import com.twitterbot.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long userId);

    List<Comment> findByUserIdAndPostId(Long aLong, Long aLong1);

    List<Comment> findByPostId(Long aLong);
}
