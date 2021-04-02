package com.carta.nbaservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carta.nbaservice.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
