package com.carta.nbaservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.entities.Match;
import com.carta.nbaservice.entities.Player;
import com.carta.nbaservice.entities.PlayerStatistics;
import com.carta.nbaservice.entities.Team;
import com.carta.nbaservice.repos.CommentRepository;
import com.carta.nbaservice.repos.GameRepository;

@ExtendWith(SpringExtension.class)
class GameServiceImplTest {

    public static final int GAME_ID = 1;
    private static final String GAME_DATE = "2021-03-28";
    public static final String COMMENT_TEXT = "some comment";

    @Mock
    private NbaService nbaService;

    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void givenGameId_whenGettingGameInfo_thenGameIsFound() {
        String datePatternWhenGettingGamesByDate = "2021-03-28 00:00:00 UTC";
        Comment comment = new Comment(GAME_ID, COMMENT_TEXT);
        List<PlayerStatistics> playerStatistics = new ArrayList<>();
        playerStatistics.add(createDummyPlayerStatisticsBasedOnFreeNBA());

        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.empty());
        when(commentRepository.findByGameIdOrderByTimestampDesc(GAME_ID)).thenReturn(Collections.singletonList(comment));
        when(nbaService.getGame(GAME_ID)).thenReturn(createDummyGameBasedOnFreeNBA(datePatternWhenGettingGamesByDate));
        when(nbaService.getPlayersFromGame(GAME_ID)).thenReturn(playerStatistics);

        Game game = gameServiceImpl.getGame(GAME_ID);

        assertThat(game.getGameId()).isEqualTo(GAME_ID);
        assertEquals(1, game.getPlayers().size());
        assertEquals(1, game.getComments().size());
    }

    @Test
    void givenDate_whenGettingGames_thenShouldReturnGamesForDate() {
        String datePatternWhenGettingGamesByDate = "2021-03-28T00:00:00.000Z";
        List<Match> matches = new ArrayList<>();
        matches.add(createDummyGameBasedOnFreeNBA(datePatternWhenGettingGamesByDate));
        matches.add(createDummyGameBasedOnFreeNBA(datePatternWhenGettingGamesByDate));
        matches.add(createDummyGameBasedOnFreeNBA(datePatternWhenGettingGamesByDate));

        when(nbaService.getAllGamesForDate(GAME_DATE)).thenReturn(matches);

        List<Game> games = gameServiceImpl.listGames(LocalDate.parse(GAME_DATE));

        assertNotNull(games);
        assertEquals(3, games.size());
    }

    private Match createDummyGameBasedOnFreeNBA(String date) {
        Team homeTeam = createDummyTeamBasedOnFreeNBA();
        Team visitorTeam = createDummyTeamBasedOnFreeNBA();
        return new Match(GAME_ID, date, homeTeam, 100, visitorTeam, 90);
    }

    private PlayerStatistics createDummyPlayerStatisticsBasedOnFreeNBA() {
        Player player = createDummyPlayerBasedOnFreeNBA();
        return new PlayerStatistics( player, 18);
    }

    private Team createDummyTeamBasedOnFreeNBA() {
        return new Team(1, "ht1", "home", "", "north", "hometeam1", "hometeam1");
    }

    private Player createDummyPlayerBasedOnFreeNBA() {
        return new Player("first", "last");
    }
}