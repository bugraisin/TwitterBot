package com.twitterbot.repositories;

import com.twitterbot.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserIdAndPostId(Long aLong, Long aLong1);

    List<Like> findByPostId(Long aLong);

    List<Like> findByUserId(Long aLong);
}
