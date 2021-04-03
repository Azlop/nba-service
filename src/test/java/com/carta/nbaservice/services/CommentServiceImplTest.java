package com.carta.nbaservice.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.entities.Team;
import com.carta.nbaservice.exceptions.CommentNotFoundException;
import com.carta.nbaservice.repos.CommentRepository;

@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {

    public static final int GAME_ID = 1;
    public static final String COMMENT_TEXT = "some comment";
    private static final Integer COMMENT_ID = 1;

    @Mock
    private NbaService nbaService;

    @InjectMocks
    private CommentServiceImpl commentServiceImpl;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void givenValidGameIdAndCommentText_whenAddingComment_thenShouldFindComment() {
        Comment comment = new Comment(GAME_ID, COMMENT_TEXT);

        when(nbaService.getGame(GAME_ID)).thenReturn(createDummyGameBasedOnFreeNBA());
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment commentResult = commentServiceImpl.addCommentToGame(GAME_ID, COMMENT_TEXT);

        assertNotNull(commentResult);
    }

    @Test
    void givenValidCommentId_whenDeletingComment_thenShouldFindAndDeleteComment() {
        Comment comment = new Comment(GAME_ID, COMMENT_TEXT);
        comment.setCommentId(COMMENT_ID);

        when(commentRepository.findById(comment.getCommentId())).thenReturn(Optional.of(comment));

        commentServiceImpl.deleteComment(COMMENT_ID);

        verify(commentRepository).deleteById(COMMENT_ID);
    }

    @Test
    void givenInvalidCommentId_whenDeletingComment_thenThrowCommentNotFoundException() {
        Comment comment = new Comment(GAME_ID, COMMENT_TEXT);
        comment.setCommentId(COMMENT_ID);

        when(commentRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(CommentNotFoundException.class, () ->
                commentServiceImpl.deleteComment(COMMENT_ID));

        String expectedMessage = "Comment ID does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    private com.carta.nbaservice.entities.Game createDummyGameBasedOnFreeNBA() {
        Team homeTeam = new Team(1, "ht1", "home", "", "north", "hometeam1", "hometeam1");
        Team visitorTeam = new Team(2, "vt2", "away", "", "south", "visitorTeam2", "visitorTeam2");
        return new com.carta.nbaservice.entities.Game(GAME_ID, "2021-03-28", homeTeam, 100, 1, false,
                2021, "final", "", visitorTeam, 90);
    }
}
