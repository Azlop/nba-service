package com.carta.nbaservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.entities.Team;
import com.carta.nbaservice.repos.CommentRepository;
import com.carta.nbaservice.repos.GameRepository;

@ExtendWith(SpringExtension.class)
class GameServiceImplTest {

    public static final int GAME_ID = 1;
    private static final String GAME_DATE = "2021-03-28";

    @Mock
    private NbaService nbaService;

    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    void givenGameId_whenGettingGameInfo_thenGameIsFound() {
        when(nbaService.getGame(GAME_ID)).thenReturn(createDummyGameBasedOnFreeNBA());

        Game game = gameServiceImpl.fetchGame(GAME_ID);

        assertThat(game.getGameId()).isEqualTo(GAME_ID);
    }

    @Test
    void givenDate_whenGettingGames_thenShouldReturnGamesForDate() {
        List<com.carta.nbaservice.entities.Game> games = new ArrayList<>();
        games.add(createDummyGameBasedOnFreeNBA());
        games.add(createDummyGameBasedOnFreeNBA());
        games.add(createDummyGameBasedOnFreeNBA());

        when(nbaService.getAllGamesForDate(GAME_DATE)).thenReturn(games);

        List<Game> gameDtos = gameServiceImpl.listGames(GAME_DATE);

        assertNotNull(gameDtos);
        assertEquals(3, gameDtos.size());
    }

    @Test
    void givenValidGameIdAndCommentText_whenAddingComment_thenShouldFindComment() {
        String commentText = "some comment";
        Optional<Game> game = gameRepository.findById(GAME_ID);
        assertNotNull(game);
    }

    private com.carta.nbaservice.entities.Game createDummyGameBasedOnFreeNBA() {
        Team homeTeam = new Team(1, "ht1", "home", "", "north", "hometeam1", "hometeam1");
        Team visitorTeam = new Team(2, "vt2", "away", "", "south", "visitorTeam2", "visitorTeam2");
        return new com.carta.nbaservice.entities.Game(GAME_ID, "2021-03-28", homeTeam, 100, 1, false,
                2021, "final", "", visitorTeam, 90);
    }
}