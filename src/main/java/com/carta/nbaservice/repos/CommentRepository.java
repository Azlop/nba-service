package com.carta.nbaservice.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.carta.nbaservice.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByGameIdOrderByTimestampDesc(@Param("gameId") int gameId);
}
