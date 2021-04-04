package com.carta.nbaservice.services;

import java.time.LocalDate;
import java.util.List;

import com.carta.nbaservice.domain.Game;

public interface GameService {
    Game getGame(int gameId);
    List<Game> listGames(LocalDate date);
}
