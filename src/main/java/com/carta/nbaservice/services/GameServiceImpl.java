package com.carta.nbaservice.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.domain.Player;
import com.carta.nbaservice.repos.CommentRepository;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private NbaService nbaService;

    private CommentRepository commentRepository;

    @Autowired
    public GameServiceImpl(NbaService nbaService) {
        this.nbaService = nbaService;
    }

    @Override
    public Game fetchGame(int gameId) {
        com.carta.nbaservice.entities.Game game = nbaService.getGame(gameId);
        List<Player> playersDtos = fetchPlayersPoints(gameId);
        Game gameDto = new Game();
        gameDto.setGameId(game.getId());
        gameDto.setDate(game.getDate());
        gameDto.setHomeTeamName(game.getHomeTeam().getName());
        gameDto.setAwayTeamName(game.getVisitorTeam().getName());
        gameDto.setHomeTeamScore(game.getHomeTeamScore());
        gameDto.setAwayTeamScore(game.getVisitorTeamScore());
        gameDto.setPlayers(playersDtos);
        return gameDto;
    }

    @Override
    public List<Game> listGames(String date) {
        List<com.carta.nbaservice.entities.Game> games = nbaService.getAllGamesForDate(date);
        List<Game> gameDtos = new ArrayList<>();

        for (com.carta.nbaservice.entities.Game game : games) {
            Game gameDto = new Game();
            gameDto.setGameId(game.getId());
            gameDto.setDate(game.getDate());
            gameDto.setHomeTeamName(game.getHomeTeam().getName());
            gameDto.setAwayTeamName(game.getVisitorTeam().getName());
            gameDto.setHomeTeamScore(game.getHomeTeamScore());
            gameDto.setAwayTeamScore(game.getVisitorTeamScore());
            gameDto.setPlayers(Collections.emptyList());
            gameDto.setComments(Collections.emptyList());
            gameDtos.add(gameDto);
        }

        return gameDtos;
    }

    @Override
    public void createComment(int gameId, String commentText) {
    }

    private List<Player> fetchPlayersPoints(int gameId) {
        List<com.carta.nbaservice.entities.Player> players = nbaService.getPlayersFromGame(gameId);
        return Collections.emptyList();
    }
}
