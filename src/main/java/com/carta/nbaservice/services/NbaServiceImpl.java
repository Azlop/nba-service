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

import com.carta.nbaservice.entities.DataGames;
import com.carta.nbaservice.entities.DataPlayers;
import com.carta.nbaservice.entities.Game;
import com.carta.nbaservice.entities.Player;
import com.carta.nbaservice.handlers.RestTemplateResponseErrorHandler;

@Service
public class NbaServiceImpl implements NbaService {

    public static final Logger LOGGER = LoggerFactory.getLogger(NbaServiceImpl.class);

    private static final String BASE_FREE_NBA_URL = "https://free-nba.p.rapidapi.com";
    private static final String GAMES_URI = BASE_FREE_NBA_URL + "/games";
    private static final String STATISTICS_URI = BASE_FREE_NBA_URL + "/stats";
    private static final String KEY = "587e392567msh1060feed2027cdbp1cd6a7jsn1633bc5de72c";
    private static final String HOST = "free-nba.p.rapidapi.com";
    private static final String HEADER_KEY = "x-rapidapi-key";
    private static final String HEADER_HOST = "x-rapidapi-host";

    private final RestTemplate restTemplate;
    private final HttpEntity<String> httpEntity;

    @Autowired
    public NbaServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER_KEY, KEY);
        httpHeaders.set(HEADER_HOST, HOST);
        this.httpEntity = new HttpEntity<>(httpHeaders);
    }

    @Override
    public List<Game> getAllGamesForDate(String date) {
        LOGGER.debug("Getting games for date: {}", date);
        String gamesForDate = String.join("?", GAMES_URI, "dates[]=\"" + date + "\"");
        ResponseEntity<DataGames> response = this.restTemplate.exchange(gamesForDate, HttpMethod.GET, this.httpEntity, DataGames.class);
        return Objects.requireNonNull(response.getBody()).getGames();
    }

    @Override
    public Game getGame(Integer gameId) {
        LOGGER.debug("Getting game for ID: {}", gameId);
        String singleGame = String.join("/", GAMES_URI, gameId.toString());
        ResponseEntity<Game> response = this.restTemplate.exchange(singleGame, HttpMethod.GET, this.httpEntity, Game.class);
        return response.getBody();
    }

    @Override
    public List<Player> getPlayersFromGame(Integer gameId) {
        LOGGER.debug("Getting players points for game ID: {}", gameId);
        String singleGame = String.join("?", STATISTICS_URI, "game_ids[]=" + gameId);
        ResponseEntity<DataPlayers> response = this.restTemplate.exchange(singleGame, HttpMethod.GET, this.httpEntity, DataPlayers.class);
        return Objects.requireNonNull(response.getBody()).getPlayers();
    }
}
