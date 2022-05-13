package com.carta.nbaservice.services;

import com.carta.nbaservice.configurations.AppConfig;
import com.carta.nbaservice.entities.DataMatches;
import com.carta.nbaservice.entities.DataPlayers;
import com.carta.nbaservice.entities.Match;
import com.carta.nbaservice.entities.PlayerStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class NbaServiceImpl implements NbaService {

    public static final Logger LOGGER = LoggerFactory.getLogger(NbaServiceImpl.class);

    private static final String GAMES_URI = "games";
    private static final String STATISTICS_URI = "stats";
    private static final int FIRST_PAGE = 1;

    private final RestTemplate restTemplate;
    private final HttpEntity<String> httpEntity;
    private final AppConfig appConfig;

    @Autowired
    public NbaServiceImpl(RestTemplate restTemplate, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;
        HttpHeaders httpHeaders = new HttpHeaders();
        appConfig.getHeaders().forEach(httpHeaders::set);
        this.httpEntity = new HttpEntity<>(httpHeaders);
    }

    @Override
    public List<Match> fetchAllGamesForDate(String date) {
        LOGGER.debug("Fetching games for date: {}", date);
        ResponseEntity<DataMatches> response = fetchDataMatchesResponseEntity(date, FIRST_PAGE);

        List<Match> games = Objects.requireNonNull(response.getBody()).getGames();
        int pageNumber = Objects.requireNonNull(response.getBody()).getMetadata().getNextPage();
        boolean hasMorePlayers = pageNumber != 0;

        while (hasMorePlayers) {
            LOGGER.debug("Fetching games for page: {}", pageNumber);
            ResponseEntity<DataMatches> nextPageResponse = fetchDataMatchesResponseEntity(date, pageNumber);
            games.addAll(Objects.requireNonNull(nextPageResponse.getBody()).getGames());
            pageNumber = Objects.requireNonNull(nextPageResponse.getBody()).getMetadata().getNextPage();
            if (pageNumber == 0) {
                hasMorePlayers = false;
            }
        }

        return games;
    }

    @Override
    public List<PlayerStatistics> fetchPlayersFromGame(Integer gameId) {
        LOGGER.debug("Fetching players for game ID: {}", gameId);
        ResponseEntity<DataPlayers> response = fetchDataPlayersResponseEntity(gameId, FIRST_PAGE);

        List<PlayerStatistics> playerStatistics = Objects.requireNonNull(response.getBody()).getPlayers();
        int pageNumber = Objects.requireNonNull(response.getBody()).getMetadata().getNextPage();
        boolean hasMorePlayers = pageNumber != 0;

        while (hasMorePlayers) {
            LOGGER.debug("Fetching players for page {}", pageNumber);
            ResponseEntity<DataPlayers> nextPageResponse = fetchDataPlayersResponseEntity(gameId, pageNumber);
            playerStatistics.addAll(Objects.requireNonNull(nextPageResponse.getBody()).getPlayers());
            pageNumber = Objects.requireNonNull(nextPageResponse.getBody()).getMetadata().getNextPage();
            if (pageNumber == 0) {
                hasMorePlayers = false;
            }
        }

        return playerStatistics;
    }

    private ResponseEntity<DataPlayers> fetchDataPlayersResponseEntity(Integer gameId, int firstPage) {
        String singleGameUrl = STATISTICS_URI + String.format("?game_ids[]=%d", gameId) + String.format("&page=%d", firstPage);
        return this.restTemplate.exchange(singleGameUrl, HttpMethod.GET, this.httpEntity, DataPlayers.class);
    }

    private ResponseEntity<DataMatches> fetchDataMatchesResponseEntity(String date, int pageNumber) {
        String gamesForDate = GAMES_URI + String.format("?dates[]=\"%s\"", date) + String.format("&page=%d", pageNumber);
        return this.restTemplate.exchange(gamesForDate, HttpMethod.GET, this.httpEntity, DataMatches.class);
    }
}