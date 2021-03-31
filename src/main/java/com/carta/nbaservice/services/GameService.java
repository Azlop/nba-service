package com.carta.nbaservice.services;

import java.util.List;

import com.carta.nbaservice.dtos.GameDto;

public interface GameService {
    GameDto fetchGame(int gameId);
    GameDto createGame(GameDto gameDto);
    List<GameDto> listGames(String date);
}
