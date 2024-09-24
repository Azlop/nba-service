package com.carta.nbaservice.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.exceptions.CommentNotFoundException;
import com.carta.nbaservice.exceptions.GameNotFoundException;
import com.carta.nbaservice.repos.CommentRepository;
import com.carta.nbaservice.repos.GameRepository;

@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {

    public static final int GAME_ID = 1;
    public static final String COMMENT_TEXT = "some comment";
    private static final Integer COMMENT_ID = 1;

    @InjectMocks
    private CommentServiceImpl commentServiceImpl;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private GameRepository gameRepository;

    @Test
    void givenExistingGameIdAndCommentText_whenAddingComment_thenShouldFindComment() {
        Comment comment = new Comment(null, GAME_ID, COMMENT_TEXT, null);
        Game game = Game.builder()
            .gameId(GAME_ID)
            .date(LocalDate.parse("2021-03-28"))
            .homeTeamName("homeTeam")
            .awayTeamName("awayTeam")
            .homeTeamScore(50)
            .awayTeamScore(51)
            .build();

        when(gameRepository.findByGameId(GAME_ID)).thenReturn(Optional.of(game));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment commentResult = commentServiceImpl.addCommentToGame(GAME_ID, COMMENT_TEXT);

        assertNotNull(commentResult);
    }

    @Test
    void givenNonExistingGameId_whenAddingComment_thenThrowGameNotFoundException() {
        when(gameRepository.findByGameId(GAME_ID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(GameNotFoundException.class, () ->
                commentServiceImpl.addCommentToGame(GAME_ID, COMMENT_TEXT));

        String expectedMessage = "Game ID does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenExistingCommentId_whenUpdatingComment_thenShouldFindAndChangeComment() {
        Comment comment = new Comment(COMMENT_ID, GAME_ID, COMMENT_TEXT, null);

        when(commentRepository.findById(comment.getCommentId())).thenReturn(Optional.of(comment));

        commentServiceImpl.modifyCommentOnGame(COMMENT_ID, COMMENT_TEXT);

        verify(commentRepository).save(comment);
    }

    @Test
    void givenValidCommentId_whenDeletingComment_thenShouldFindAndDeleteComment() {
        Comment comment = new Comment(COMMENT_ID, GAME_ID, COMMENT_TEXT, null);
        when(commentRepository.findById(comment.getCommentId())).thenReturn(Optional.of(comment));

        commentServiceImpl.deleteComment(COMMENT_ID);

        verify(commentRepository).deleteById(COMMENT_ID);
    }

    @Test
    void givenInvalidCommentId_whenDeletingComment_thenThrowCommentNotFoundException() {
        when(commentRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(CommentNotFoundException.class, () ->
                commentServiceImpl.deleteComment(COMMENT_ID));

        String expectedMessage = "Comment ID does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}