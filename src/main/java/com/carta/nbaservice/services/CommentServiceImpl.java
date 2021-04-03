package com.carta.nbaservice.services;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.exceptions.CommentNotFoundException;
import com.carta.nbaservice.repos.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private NbaService nbaService;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment addCommentToGame(Integer gameId, String commentText) {
        verifyGameId(gameId);
        Comment comment = new Comment(gameId, commentText);
        LOGGER.debug("Creating comment: {}", comment);
        return commentRepository.save(comment);
    }

    @Override
    public Comment modifyCommentOnGame(Integer commentId, String commentText) {
        Comment comment = verifyCommentId(commentId);
        comment.setText(commentText);
        LOGGER.debug("Updating comment: {}", comment);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(int commentId) {
        LOGGER.debug("Deleting comment for ID: {}", commentId);
        Comment comment = verifyCommentId(commentId);
        commentRepository.deleteById(comment.getCommentId());
        LOGGER.debug("Successfully deleted comment");
    }

    private Comment verifyCommentId(Integer commentId) {
        LOGGER.debug("Verifying existence of comment for ID: {}", commentId);
        return commentRepository.findById(commentId).orElseThrow(() -> {
                LOGGER.error("Comment ID not found: {}", commentId);
                throw new CommentNotFoundException(commentId);
            });
    }

    private void verifyGameId(Integer gameId) {
        LOGGER.debug("Verifying existence of game for ID: {}", gameId);
        com.carta.nbaservice.entities.Game game = nbaService.getGame(gameId);
        if (game == null) {
            LOGGER.error("Game ID not found: {}", gameId);
            throw new NoSuchElementException();
        }
        //        gameRepository.findById(gameId).orElseThrow(() ->
        //                new NoSuchElementException("Game ID does not exist: " + gameId);
    }
}
