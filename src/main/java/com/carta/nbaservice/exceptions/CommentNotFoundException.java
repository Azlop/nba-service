package com.carta.nbaservice.exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Integer commentId) {
        super("Comment ID does not exist: " + commentId);
    }
}
