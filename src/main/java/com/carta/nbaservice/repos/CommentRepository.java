package com.carta.nbaservice.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.carta.nbaservice.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findByGameIdOrderByTimestampDesc(@Param("gameId") int gameId);
}
