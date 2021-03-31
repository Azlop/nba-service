package com.carta.nbaservice.services;

import java.util.List;

import com.carta.nbaservice.entities.Game;
import com.carta.nbaservice.entities.Player;

public interface NbaService {
    List<Game> getAllGamesForDate(String date);
    Game getGame(Integer gameId);
    List<Player> getPlayersFromGame(long gameId);
}
