package com.carta.nbaservice.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.carta.nbaservice.dtos.CommentDto;
import com.carta.nbaservice.dtos.GameDto;
import com.carta.nbaservice.dtos.PlayerDto;
import com.carta.nbaservice.services.GameService;

@WebMvcTest(GameController.class)
class GameControllerTest {

    public static final int GAME_ID = 1;
    public static final int COMMENT_ID = 1;
    public static final String COMMENT_TEXT = "It was bad!";
    public static final int PLAYER_ID = 2;
    public static final String PLAYER_FIRST_NAME = "James";
    public static final String PLAYER_LAST_NAME = "Harden";
    public static final int PLAYER_POINTS = 30;
    public static final String GAME_DATE = "2021-03-28";
    public static final String HOME_TEAM_NAME = "Suns";
    public static final String AWAY_TEAM_NAME = "Warriors";
    public static final int HOME_TEAM_SCORE = 100;
    public static final int AWAY_TEAM_SCORE = 90;
    private static final String COMMENT_TIMESTAMP = "2021-03-28T00:00:00.000+00:00";
    private final List<GameDto> games = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    void givenGameId_whenGettingGame_thenShouldReturnGameInfo() throws Exception {
        GameDto gameDto = createGame();

        given(gameService.fetchGame(GAME_ID)).willReturn(gameDto);

        mockMvc.perform(get("/games/"+ GAME_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(GAME_ID))
                .andExpect(jsonPath("$.date").value(GAME_DATE))
                .andExpect(jsonPath("$.homeTeamName").value(HOME_TEAM_NAME))
                .andExpect(jsonPath("$.awayTeamName").value(AWAY_TEAM_NAME))
                .andExpect(jsonPath("$.homeTeamScore").value(HOME_TEAM_SCORE))
                .andExpect(jsonPath("$.awayTeamScore").value(AWAY_TEAM_SCORE))
                .andExpect(jsonPath("$.comments[0].commentId").value(COMMENT_ID))
                .andExpect(jsonPath("$.comments[0].text").value(COMMENT_TEXT))
                .andExpect(jsonPath("$.comments[0].timestamp").value(COMMENT_TIMESTAMP))
                .andExpect(jsonPath("$.players[0].playerId").value(PLAYER_ID))
                .andExpect(jsonPath("$.players[0].firstName").value(PLAYER_FIRST_NAME))
                .andExpect(jsonPath("$.players[0].lastName").value(PLAYER_LAST_NAME))
                .andExpect(jsonPath("$.players[0].points").value(PLAYER_POINTS));
    }

    @Test
    void givenDate_whenGettingGames_thenShouldReturnGameInfoByDateGiven() throws Exception {
        games.add(createGame());

        given(gameService.listGames(GAME_DATE)).willReturn(games);

        mockMvc.perform(get("/games")
                        .param("date", GAME_DATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private GameDto createGame() {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(COMMENT_ID);
        commentDto.setText(COMMENT_TEXT);
        commentDto.setTimestamp(Timestamp.valueOf(LocalDateTime.of(2021, 3, 28, 0, 0)));

        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerId(PLAYER_ID);
        playerDto.setFirstName(PLAYER_FIRST_NAME);
        playerDto.setLastName(PLAYER_LAST_NAME);
        playerDto.setPoints(PLAYER_POINTS);

        GameDto gameDto = new GameDto();
        gameDto.setGameId(GAME_ID);
        gameDto.setDate(GAME_DATE);
        gameDto.setHomeTeamName(HOME_TEAM_NAME);
        gameDto.setAwayTeamName(AWAY_TEAM_NAME);
        gameDto.setHomeTeamScore(HOME_TEAM_SCORE);
        gameDto.setAwayTeamScore(AWAY_TEAM_SCORE);
        gameDto.setComments(Collections.singletonList(commentDto));
        gameDto.setPlayers(Collections.singletonList(playerDto));

        return gameDto;
    }

}