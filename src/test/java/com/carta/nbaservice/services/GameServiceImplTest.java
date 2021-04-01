package com.carta.nbaservice.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carta.nbaservice.dtos.CommentDto;
import com.carta.nbaservice.dtos.GameDto;
import com.carta.nbaservice.dtos.PlayerDto;
import com.carta.nbaservice.repos.GameRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameServiceImplTest {

    public static final int GAME_ID = 1;

    @Autowired
    private GameService gameService;

    @MockBean
    private GameRepository gameRepository;

    @BeforeAll
    void setUp() {
        CommentDto commentDto = new CommentDto("Good game", new Timestamp(System.currentTimeMillis()));
        PlayerDto playerDto = new PlayerDto("LeBron", "James", 23);
        GameDto gameDto = new GameDto(GAME_ID, "2021-03-28", "Suns", "Warriors",
                102, 120, Collections.singletonList(commentDto), Collections.singletonList(playerDto));
        Mockito.when(gameRepository.save(gameDto)).thenReturn(gameDto);
    }

    @Test
    void givenOneGame_whenSavingGame_thenGameIsFound() {
        GameDto game = gameService.fetchGame(GAME_ID);

        assertThat(game.getGameId()).isEqualTo(GAME_ID);
    }
}