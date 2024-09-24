package com.carta.nbaservice.controllers;

import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.domain.Player;
import com.carta.nbaservice.services.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    public static final int GAME_ID = 1;
    public static final String PLAYER_FIRST_NAME = "James";
    public static final String PLAYER_LAST_NAME = "Harden";
    public static final String GAME_DATE = "2021-03-28";
    public static final String HOME_TEAM_NAME = "Suns";
    public static final String AWAY_TEAM_NAME = "Warriors";
    public static final int HOME_TEAM_SCORE = 100;
    public static final int AWAY_TEAM_SCORE = 90;
    private final List<Game> games = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    void givenGameId_whenGettingGame_thenShouldReturnGameInfo() throws Exception {
        Game game = createGame();

        given(gameService.getGame(GAME_ID)).willReturn(game);

        mockMvc.perform(get("/games/"+ GAME_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(GAME_ID))
                .andExpect(jsonPath("$.date").value(GAME_DATE))
                .andExpect(jsonPath("$.homeTeamName").value(HOME_TEAM_NAME))
                .andExpect(jsonPath("$.awayTeamName").value(AWAY_TEAM_NAME))
                .andExpect(jsonPath("$.homeTeamScore").value(HOME_TEAM_SCORE))
                .andExpect(jsonPath("$.awayTeamScore").value(AWAY_TEAM_SCORE))
                .andExpect(jsonPath("$.comments").isEmpty())
                .andExpect(jsonPath("$.gamePoints").isEmpty());
    }

    @Test
    void givenDate_whenGettingGames_thenShouldReturnGameInfoByDateGiven() throws Exception {
        games.add(createGame());

        given(gameService.listGames(LocalDate.parse(GAME_DATE))).willReturn(games);

        mockMvc.perform(get("/games")
                        .param("date", GAME_DATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Game createGame() {
        Player player = new Player();
        player.setFirstName(PLAYER_FIRST_NAME);
        player.setLastName(PLAYER_LAST_NAME);

        return Game.builder()
            .gameId(GAME_ID)
            .date(LocalDate.parse(GAME_DATE))
            .homeTeamName(HOME_TEAM_NAME)
            .awayTeamName(AWAY_TEAM_NAME)
            .homeTeamScore(HOME_TEAM_SCORE)
            .awayTeamScore(AWAY_TEAM_SCORE)
            .build();
    }

}