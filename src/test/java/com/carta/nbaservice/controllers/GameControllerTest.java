package com.carta.nbaservice.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.Collections;

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

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    public void givenGameId_whenGettingGame_thenShouldReturnGameInfo() throws Exception {
        int gameId = 1;
        GameDto gameDto = createGame();

        given(gameService.fetchGame(gameId)).willReturn(gameDto);

        mockMvc.perform(get("/games/"+gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(1));
    }

    private GameDto createGame() {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(1);
        commentDto.setText("It was bad!");
        commentDto.setTimestamp(new Timestamp(System.currentTimeMillis()));

        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerId(2);
        playerDto.setFirstName("James");
        playerDto.setLastName("Harden");
        playerDto.setPoints(30);

        GameDto gameDto = new GameDto();
        gameDto.setGameId(3);
        gameDto.setDate("2021-03-28");
        gameDto.setHomeTeamName("Suns");
        gameDto.setAwayTeamName("Warriors");
        gameDto.setHomeTeamScore(100);
        gameDto.setAwayTeamScore(90);
        gameDto.setComments(Collections.singletonList(commentDto));
        gameDto.setPlayers(Collections.singletonList(playerDto));

        return gameDto;
    }

}