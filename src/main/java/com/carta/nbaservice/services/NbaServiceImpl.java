package com.carta.nbaservice.services;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.carta.nbaservice.configurations.AppConfig;
import com.carta.nbaservice.entities.DataMatches;
import com.carta.nbaservice.entities.DataPlayers;
import com.carta.nbaservice.entities.Match;
import com.carta.nbaservice.entities.PlayerStatistics;
import com.carta.nbaservice.handlers.RestTemplateResponseErrorHandler;

@Service
public class NbaServiceImpl implements NbaService {

    public static final Logger LOGGER = LoggerFactory.getLogger(NbaServiceImpl.class);

    private static final String BASE_FREE_NBA_URL = "https://free-nba.p.rapidapi.com";
    private static final String GAMES_URI = BASE_FREE_NBA_URL + "/games";
    private static final String STATISTICS_URI = BASE_FREE_NBA_URL + "/stats";
    private static final String HOST = "free-nba.p.rapidapi.com";
    private static final String HEADER_KEY = "x-rapidapi-key";
    private static final String HEADER_HOST = "x-rapidapi-host";
    private static final int FIRST_PAGE = 1;

    private final RestTemplate restTemplate;
    private final HttpEntity<String> httpEntity;

    @Autowired
    public NbaServiceImpl(RestTemplateBuilder restTemplateBuilder, AppConfig appConfig) {
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER_KEY, appConfig.getKey());
        httpHeaders.set(HEADER_HOST, HOST);
        this.httpEntity = new HttpEntity<>(httpHeaders);
    }

    @Override
    public List<Match> fetchAllGamesForDate(String date) {
        LOGGER.debug("Getting games for date: {}", date);
        ResponseEntity<DataMatches> response = fetchDataMatchesResponseEntity(date, FIRST_PAGE);

        List<Match> games = Objects.requireNonNull(response.getBody()).getGames();
        int pageNumber = Objects.requireNonNull(response.getBody()).getMetadata().getNextPage();
        boolean hasMorePlayers = pageNumber != 0;

        while (hasMorePlayers) {
            LOGGER.debug("Getting games for page: {}", pageNumber);
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
    public Match fetchGame(Integer gameId) {
        LOGGER.debug("Getting game for ID: {}", gameId);
        String singleGame = String.join("/", GAMES_URI, gameId.toString());
        ResponseEntity<Match> response = this.restTemplate.exchange(singleGame, HttpMethod.GET, this.httpEntity, Match.class);
        return response.getBody();
    }

    @Override
    public List<PlayerStatistics> fetchPlayersFromGame(Integer gameId) {
        LOGGER.debug("Getting players for game ID: {}", gameId);
        ResponseEntity<DataPlayers> response = fetchDataPlayersResponseEntity(gameId, FIRST_PAGE);

        List<PlayerStatistics> playerStatistics = Objects.requireNonNull(response.getBody()).getPlayers();
        int pageNumber = Objects.requireNonNull(response.getBody()).getMetadata().getNextPage();
        boolean hasMorePlayers = pageNumber != 0;

        while (hasMorePlayers) {
            LOGGER.debug("Getting players for page {}", pageNumber);
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
