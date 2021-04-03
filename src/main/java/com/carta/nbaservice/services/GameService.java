package com.carta.nbaservice.services;

import java.util.List;

import com.carta.nbaservice.domain.Game;

public interface GameService {
    Game fetchGame(int gameId);
    List<Game> listGames(String date);
}
