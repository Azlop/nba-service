package com.carta.nbaservice.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import com.carta.nbaservice.entities.Match;
import com.carta.nbaservice.entities.PlayerStatistics;

@SpringBootTest
class NbaServiceImplTest {

    @Autowired
    private NbaService nbaService;

    @Test
    void givenGameId_whenGettingGame_thenShouldReturnOneGame() {
        int gameId = 264402;

        Match match = nbaService.getGame(gameId);

        assertEquals("2021-03-28 00:00:00 UTC", match.getDate());
        assertEquals("Raptors", match.getHomeTeam().getName());
        assertEquals("Trail Blazers", match.getVisitorTeam().getName());
        assertEquals(117, match.getHomeTeamScore());
        assertEquals(122, match.getVisitorTeamScore());
    }

    @Test
    void givenNonExistingGameId_whenGettingGame_thenShouldReturnNotFound() {
        int gameId = Integer.MAX_VALUE;

        Exception exception = assertThrows(HttpClientErrorException.class, () -> nbaService.getGame(gameId));

        String expectedMessage = "Game ID not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenDate_whenGettingGames_thenShouldReturnAllGamesForTheDate() {
        String date = "2021-03-28";

        List<Match> matches = nbaService.getAllGamesForDate(date);

        assertThat(matches, hasSize(4));
    }

    @Test
    void givenGameIdWithTwoPages_whenGettingPlayerStatistics_thenShouldReturnPlayersStatisticsForGame() {
        Integer gameId = 264405;

        List<PlayerStatistics> players = nbaService.getPlayersFromGame(gameId);

        assertThat(players, hasSize(30));
    }
}