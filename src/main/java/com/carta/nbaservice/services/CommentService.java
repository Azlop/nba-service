package com.carta.nbaservice.services;

import com.carta.nbaservice.domain.Comment;

public interface CommentService {
    Comment addCommentToGame(Integer gameId, String commentText);
    Comment modifyCommentOnGame(Integer commentId, String commentText);
    void deleteComment(int commentId);
}
