package com.carta.nbaservice.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.repos.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private NbaService nbaService;

    @Autowired
    private GameService gameService;

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public Comment addCommentToGame(Integer gameId, String commentText) {
        verifyGameId(gameId);
        Comment comment = new Comment(gameId, commentText);
        return commentRepository.save(comment);
    }

    @Override
    public Comment modifyCommentOnGame(Integer commentId, String commentText) {
        Comment comment = verifyCommentId(commentId);
        comment.setText(commentText);
        return commentRepository.save(comment);
    }

    private Comment verifyCommentId(Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new NoSuchElementException("Comment ID does not exist: " + commentId));
    }

    private void verifyGameId(Integer gameId) {
        com.carta.nbaservice.entities.Game game = nbaService.getGame(gameId);
        if (game == null) {
            throw new NoSuchElementException("Game ID does not exist: " + gameId);
        }
        //        gameRepository.findById(gameId).orElseThrow(() ->
        //                new NoSuchElementException("Game ID does not exist: " + gameId);
    }
}
