package com.carta.nbaservice.services;

import java.sql.Timestamp;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.carta.nbaservice.dtos.CommentDto;
import com.carta.nbaservice.dtos.GameDto;
import com.carta.nbaservice.dtos.PlayerDto;
import com.carta.nbaservice.repos.GameRepository;

@SpringBootTest
class GameServiceImplTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    void givenOneGame_whenSavingGame_thenGameIsSavedInDB() {
        CommentDto commentDto = new CommentDto("Good game", new Timestamp(System.currentTimeMillis()));
        PlayerDto playerDto = new PlayerDto("LeBron", "James", 23);
        GameDto gameDto = new GameDto(1, "2021-03-28", "Suns", "Warriors",
                102, 120, Collections.singletonList(commentDto), Collections.singletonList(playerDto));

        gameDto = gameRepository.save(gameDto);

        assertNotNull(gameDto.getGameId());
    }
}