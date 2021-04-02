package com.carta.nbaservice.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.dtos.CommentDto;
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
    public Game getGame(@PathVariable(value = "gameId") int gameId) {
        return gameService.fetchGame(gameId);
    }

    @GetMapping
    public List<Game> getAllGamesByDate(@RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return gameService.listGames(date.toString());
    }

    @PostMapping(value = "/{gameId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addCommentToGame(@PathVariable(value = "gameId") int gameId,
            @RequestBody @Validated CommentDto commentDto) {

        return gameService.addCommentToGame(gameId, commentDto.getText());
    }

    @PutMapping(value = "/{gameId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public Comment modifyCommentOnGame(@PathVariable(value = "gameId") int gameId,
            @RequestBody @Validated CommentDto commentDto) {

        return gameService.modifyCommentOnGame(gameId, commentDto.getText());
    }
}
