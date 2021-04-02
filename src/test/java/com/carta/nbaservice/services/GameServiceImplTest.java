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

import com.carta.nbaservice.dtos.GameDto;
import com.carta.nbaservice.entities.Game;
import com.carta.nbaservice.entities.Team;

@ExtendWith(SpringExtension.class)
class GameServiceImplTest {

    public static final int GAME_ID = 1;
    private static final String GAME_DATE = "2021-03-28";

    @Mock
    private NbaService nbaService;

    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    @Test
    public void givenGameId_whenGettingGameInfo_thenGameIsFound() {
        when(nbaService.getGame(GAME_ID)).thenReturn(getDummyGame());

        GameDto game = gameServiceImpl.fetchGame(GAME_ID);

        assertThat(game.getGameId()).isEqualTo(GAME_ID);
    }

    @Test
    public void givenDate_whenGettingGames_thenShouldReturnGamesForDate() {
        List<Game> games = new ArrayList<>();
        games.add(getDummyGame());
        games.add(getDummyGame());
        games.add(getDummyGame());

        when(nbaService.getAllGamesForDate(GAME_DATE)).thenReturn(games);

        List<GameDto> gameDtos = gameServiceImpl.listGames(GAME_DATE);

        assertNotNull(gameDtos);
        assertEquals(3, gameDtos.size());
    }

    private Game getDummyGame() {
        Team homeTeam = new Team(1, "ht1", "home", "", "north", "hometeam1", "hometeam1");
        Team visitorTeam = new Team(2, "vt2", "away", "", "south", "visitorTeam2", "visitorTeam2");
        return new Game(GAME_ID, "2021-03-28", homeTeam, 100, 1, false,
                2021, "final", "", visitorTeam, 90);
    }
}