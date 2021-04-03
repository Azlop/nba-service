package com.carta.nbaservice.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.entities.Team;
import com.carta.nbaservice.repos.CommentRepository;

@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {

    public static final int GAME_ID = 1;
    public static final String COMMENT_TEXT = "some comment";

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
    @Disabled("not implemented yet")
    void givenInvalidGameId_whenAddingComment_thenNotFoundIsThrown() {

    }

    private com.carta.nbaservice.entities.Game createDummyGameBasedOnFreeNBA() {
        Team homeTeam = new Team(1, "ht1", "home", "", "north", "hometeam1", "hometeam1");
        Team visitorTeam = new Team(2, "vt2", "away", "", "south", "visitorTeam2", "visitorTeam2");
        return new com.carta.nbaservice.entities.Game(GAME_ID, "2021-03-28", homeTeam, 100, 1, false,
                2021, "final", "", visitorTeam, 90);
    }
}
