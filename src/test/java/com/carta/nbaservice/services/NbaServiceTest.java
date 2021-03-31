package com.carta.nbaservice.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.carta.nbaservice.entities.Game;

@SpringBootTest
class NbaServiceTest {

    @Autowired
    private NbaService nbaService;

    @Test
    void givenGameId_whenGettingGame_thenShouldReturnOneGame() {
        int gameId = 264402;

        Game game = nbaService.getGame(gameId);

        assertEquals("2021-03-28 00:00:00 UTC", game.getDate());
        assertEquals(117, game.getHomeTeamScore());
        assertEquals(4, game.getPeriod());
        assertFalse(game.isPostseason());
        assertEquals(2020, game.getSeason());
        assertEquals("Final", game.getStatus());
        assertEquals("", game.getTime());
        assertEquals(122, game.getVisitorTeamScore());
    }

    @Test
    void givenDate_whenGettingGames_thenShouldReturnAllGamesForTheDate() {
        String date = "2021-03-28";

        List<Game> games = nbaService.getAllGamesForDate(date);

        assertThat(games, hasSize(4));
    }
}