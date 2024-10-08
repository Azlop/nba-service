package com.carta.nbaservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
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
import com.carta.nbaservice.exceptions.GameNotFoundException;
import com.carta.nbaservice.repos.CommentRepository;
import com.carta.nbaservice.repos.GameRepository;
import com.carta.nbaservice.repos.PlayerPointsRepository;
import com.carta.nbaservice.repos.PlayerRepository;

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

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerPointsRepository playerPointsRepository;

    @Test
    void givenGameId_whenGettingGameInfo_thenGameIsFound() {
        Comment comment = new Comment(null, GAME_ID, COMMENT_TEXT, null);
        List<PlayerStatistics> playerStatistics = new ArrayList<>();
        playerStatistics.add(createDummyPlayerStatisticsBasedOnFreeNBA());
        Game game = Game.builder()
            .gameId(GAME_ID)
            .date(LocalDate.of(2021, 3, 28))
            .homeTeamName("homeTeam")
            .awayTeamName("awayTeam")
            .homeTeamScore(100)
            .awayTeamScore(90)
            .build();

        when(gameRepository.findByGameId(GAME_ID)).thenReturn(Optional.of(game));
        when(commentRepository.findByGameIdOrderByTimestampDesc(GAME_ID)).thenReturn(Collections.singletonList(comment));
        when(nbaService.fetchPlayersFromGame(GAME_ID)).thenReturn(playerStatistics);
        when(playerRepository.saveAll(anyList())).thenReturn(null);
        when(playerPointsRepository.saveAll(anyList())).thenReturn(null);

        Game gameResult = gameServiceImpl.getGame(GAME_ID);

        assertThat(gameResult.getGameId()).isEqualTo(GAME_ID);
    }

    @Test
    void givenGameIdNotInDB_whenGettingGameInfo_thenThrowGameNotFoundException() {
        when(gameRepository.findByGameId(GAME_ID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(GameNotFoundException.class, () ->
                gameServiceImpl.getGame(GAME_ID));

        String expectedMessage = "Game ID does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenGameIdWithInvalidDate_whenGettingGameInfo_thenDateIsNotNull() {
        String invalidDate = "2021-Mar-28 0:00:00 UTC";
        Comment comment = new Comment(null, GAME_ID, COMMENT_TEXT, null);
        List<PlayerStatistics> playerStatistics = new ArrayList<>();
        playerStatistics.add(createDummyPlayerStatisticsBasedOnFreeNBA());
        List<Match> matches = new ArrayList<>();
        matches.add(createDummyGameBasedOnFreeNBA(invalidDate));

        when(gameRepository.findByGameId(GAME_ID)).thenReturn(Optional.empty());
        when(nbaService.fetchAllGamesForDate(GAME_DATE)).thenReturn(matches);
        when(commentRepository.findByGameIdOrderByTimestampDesc(GAME_ID)).thenReturn(Collections.singletonList(comment));
        when(nbaService.fetchPlayersFromGame(GAME_ID)).thenReturn(playerStatistics);
        when(playerRepository.saveAll(anyList())).thenReturn(null);
        when(playerPointsRepository.saveAll(anyList())).thenReturn(null);

        List<Game> gameResult = gameServiceImpl.listGames(LocalDate.parse(GAME_DATE));

        assertNotNull(gameResult.get(0).getDate());
    }

    @Test
    void givenDate_whenGettingGames_thenShouldReturnGamesForDate() {
        String datePatternWhenGettingGamesByDate = "2021-03-28T00:00:00.000Z";
        List<PlayerStatistics> playerStatistics = new ArrayList<>();
        playerStatistics.add(createDummyPlayerStatisticsBasedOnFreeNBA());
        List<Match> matches = new ArrayList<>();
        matches.add(createDummyGameBasedOnFreeNBA(datePatternWhenGettingGamesByDate));
        matches.add(createDummyGameBasedOnFreeNBA(datePatternWhenGettingGamesByDate));
        matches.add(createDummyGameBasedOnFreeNBA(datePatternWhenGettingGamesByDate));

        when(nbaService.fetchAllGamesForDate(GAME_DATE)).thenReturn(matches);
        when(nbaService.fetchPlayersFromGame(GAME_ID)).thenReturn(playerStatistics);

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