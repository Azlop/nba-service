package com.carta.nbaservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.entities.Player;
import com.carta.nbaservice.entities.PlayerStatistics;
import com.carta.nbaservice.entities.Team;
import com.carta.nbaservice.repos.CommentRepository;

@ExtendWith(SpringExtension.class)
class GameServiceImplTest {

    public static final int GAME_ID = 1;
    private static final String GAME_DATE = "2021-03-28";

    @Mock
    private NbaService nbaService;

    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void givenGameId_whenGettingGameInfo_thenGameIsFound() {
        List<PlayerStatistics> playerStatistics = new ArrayList<>();
        playerStatistics.add(createDummyPlayerStatisticsBasedOnFreeNBA());

        when(nbaService.getGame(GAME_ID)).thenReturn(createDummyGameBasedOnFreeNBA());
        when(nbaService.getPlayersFromGame(GAME_ID)).thenReturn(playerStatistics);

        Game game = gameServiceImpl.getGame(GAME_ID);

        assertThat(game.getGameId()).isEqualTo(GAME_ID);
        assertEquals(1, game.getPlayers().size());
        assertEquals(0, game.getComments().size());
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

    private com.carta.nbaservice.entities.Game createDummyGameBasedOnFreeNBA() {
        Team homeTeam = createDummyTeamBasedOnFreeNBA();
        Team visitorTeam = createDummyTeamBasedOnFreeNBA();
        return new com.carta.nbaservice.entities.Game(GAME_ID, "2021-03-28", homeTeam, 100, 1, false,
                2021, "final", "", visitorTeam, 90);
    }

    private PlayerStatistics createDummyPlayerStatisticsBasedOnFreeNBA() {
        com.carta.nbaservice.entities.Game game = createDummyGameBasedOnFreeNBA();
        Team homeTeam = createDummyTeamBasedOnFreeNBA();
        Player player = createDummyPlayerBasedOnFreeNBA();
        return new PlayerStatistics(1, 2, 3, 4, 5.0, 6, 7, 8.0, 9, 10, 11.0,
        12, 13, 14, game, "15", 16, 17, player, 18, 19, 20, homeTeam, 21);
    }

    private Team createDummyTeamBasedOnFreeNBA() {
        return new Team(1, "ht1", "home", "", "north", "hometeam1", "hometeam1");
    }

    private Player createDummyPlayerBasedOnFreeNBA() {
        return new Player(3, "first", 4, 5, "last", "F", 1, 6);
    }
}