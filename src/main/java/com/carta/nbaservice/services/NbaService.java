package com.carta.nbaservice.services;

import java.util.List;

import com.carta.nbaservice.entities.Match;
import com.carta.nbaservice.entities.PlayerStatistics;

public interface NbaService {
    List<Match> fetchAllGamesForDate(String date);
    Match fetchGame(Integer gameId);
    List<PlayerStatistics> fetchPlayersFromGame(Integer gameId);
}
