package com.carta.nbaservice.services;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.exceptions.CommentNotFoundException;
import com.carta.nbaservice.exceptions.GameNotFoundException;
import com.carta.nbaservice.repos.CommentRepository;
import com.carta.nbaservice.repos.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CommentServiceTest {

    public static final int GAME_ID = 1;
    public static final String COMMENT_TEXT = "some comment";
    private static final Integer COMMENT_ID = 1;

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private GameRepository gameRepository;

    @Test
    void givenExistingGameIdAndCommentText_whenAddingComment_thenShouldFindComment() {
        Comment comment = new Comment();
        comment.setGameId(GAME_ID);
        comment.setText(COMMENT_TEXT);
        Game game = new Game();
        game.setGameId(GAME_ID);
        game.setDate(LocalDate.parse("2021-03-28"));
        game.setHomeTeamName("homeTeam");
        game.setAwayTeamName("awayTeam");
        game.setHomeTeamScore(50);
        game.setAwayTeamScore(51);

        when(gameRepository.findByGameId(GAME_ID)).thenReturn(Optional.of(game));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment commentResult = commentService.addCommentToGame(GAME_ID, COMMENT_TEXT);

        assertNotNull(commentResult);
    }

    @Test
    void givenNonExistingGameId_whenAddingComment_thenThrowGameNotFoundException() {
        when(gameRepository.findByGameId(GAME_ID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(GameNotFoundException.class, () ->
                commentService.addCommentToGame(GAME_ID, COMMENT_TEXT));

        String expectedMessage = "Game ID does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenExistingCommentId_whenUpdatingComment_thenShouldFindAndChangeComment() {
        Comment comment = new Comment();
        comment.setCommentId(COMMENT_ID);
        comment.setGameId(GAME_ID);
        comment.setText(COMMENT_TEXT);

        when(commentRepository.findById(comment.getCommentId())).thenReturn(Optional.of(comment));

        commentService.modifyCommentOnGame(COMMENT_ID, COMMENT_TEXT);

        verify(commentRepository).save(comment);
    }

    @Test
    void givenValidCommentId_whenDeletingComment_thenShouldFindAndDeleteComment() {
        Comment comment = new Comment();
        comment.setCommentId(COMMENT_ID);
        comment.setGameId(GAME_ID);
        comment.setText(COMMENT_TEXT);
        when(commentRepository.findById(comment.getCommentId())).thenReturn(Optional.of(comment));

        commentService.deleteComment(COMMENT_ID);

        verify(commentRepository).deleteById(COMMENT_ID);
    }

    @Test
    void givenInvalidCommentId_whenDeletingComment_thenThrowCommentNotFoundException() {
        when(commentRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(CommentNotFoundException.class, () ->
                commentService.deleteComment(COMMENT_ID));

        String expectedMessage = "Comment ID does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}