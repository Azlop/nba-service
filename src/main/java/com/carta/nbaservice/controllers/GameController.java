package com.carta.nbaservice.controllers;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carta.nbaservice.dtos.GameDto;
import com.carta.nbaservice.services.GameService;

@RestController
@RequestMapping(path = "/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "/{gameId}")
    public GameDto getGame(@PathVariable(value = "gameId") int gameId) {
        return gameService.fetchGame(gameId);
    }

    @GetMapping
    public List<GameDto> getAllGamesByDate(@RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return gameService.listGames(date.toString());
    }
}
