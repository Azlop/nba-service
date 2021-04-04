package com.carta.nbaservice.services;

import java.util.List;

import com.carta.nbaservice.entities.Match;
import com.carta.nbaservice.entities.PlayerStatistics;

public interface NbaService {
    List<Match> getAllGamesForDate(String date);
    Match getGame(Integer gameId);
    List<PlayerStatistics> getPlayersFromGame(Integer gameId);
}
