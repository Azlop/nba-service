package com.carta.nbaservice.services;

import java.util.Collections;
import java.util.List;

import com.carta.nbaservice.dtos.GameDto;
import com.carta.nbaservice.dtos.PlayerDto;
import com.carta.nbaservice.entities.Game;

public class GameServiceImpl implements GameService {

    private NbaService nbaService;

    @Override
    public GameDto fetchGame(int gameId) {
        Game game = nbaService.getGame(gameId);
        List<PlayerDto> playerDtos = fetchPlayersPoints(gameId);
        GameDto gameDto = new GameDto();
        gameDto.setGameId(game.getId());
        gameDto.setDate(game.getDate());
        gameDto.setHomeTeamName(game.getHomeTeam().getName());
        gameDto.setAwayTeamName(game.getVisitorTeam().getName());
        gameDto.setHomeTeamScore(game.getHomeTeamScore());
        gameDto.setAwayTeamScore(game.getVisitorTeamScore());
        return gameDto;
    }

    private List<PlayerDto> fetchPlayersPoints(int gameId) {
        return Collections.emptyList();
    }

    @Override
    public GameDto createGame(GameDto gameDto) {

        return null;
    }

    @Override
    public List<GameDto> listGames(String date) {
        return null;
    }
}
