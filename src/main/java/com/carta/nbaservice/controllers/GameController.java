package com.carta.nbaservice.controllers;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.services.GameService;

@RestController
@RequestMapping(path = "/games")
public class GameController {

    public static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "/{gameId}")
    public Game getGame(@PathVariable(value = "gameId") int gameId) {
        LOGGER.info("Accessing GET Game endpoint for ID: {}", gameId);
        return gameService.fetchGame(gameId);
    }

    @GetMapping
    public List<Game> getAllGamesByDate(@RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        LOGGER.info("Accessing GET Game list endpoint for date: {}", date);
        return gameService.listGames(date.toString());
    }
}
