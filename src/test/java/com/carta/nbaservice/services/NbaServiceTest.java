package com.carta.nbaservice.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.carta.nbaservice.entities.Data;
import com.carta.nbaservice.entities.Game;

@SpringBootTest
class NbaServiceTest {

    @Autowired
    private NbaService nbaService;

    @Test
    void givenAnId_whenGettingGame_thenShouldReturnOneGame() {
        Game game = nbaService.getGame(264402);

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
        Data gamesData = nbaService.getAllGamesForDate("2021-03-28");

        assertThat(gamesData.getGames(), hasSize(4));
    }
}