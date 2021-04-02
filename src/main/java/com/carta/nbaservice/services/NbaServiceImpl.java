package com.carta.nbaservice.services;

import java.util.List;
import java.util.Objects;

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
import com.carta.nbaservice.handler.RestTemplateResponseErrorHandler;

@Service
public class NbaServiceImpl implements NbaService {

    private final String BASE_FREE_NBA_URL = "https://free-nba.p.rapidapi.com";
    private final String GAMES_URI = BASE_FREE_NBA_URL + "/games";
    private final String STATISTICS_URI = BASE_FREE_NBA_URL + "/stats";
    private final String KEY = "587e392567msh1060feed2027cdbp1cd6a7jsn1633bc5de72c";
    private final String HOST = "free-nba.p.rapidapi.com";
    private final String HEADER_KEY = "x-rapidapi-key";
    private final String HEADER_HOST = "x-rapidapi-host";

    private final RestTemplate restTemplate;

    @Autowired
    public NbaServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler())
                                    .build();
    }

    @Override
    public List<Game> getAllGamesForDate(String date) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER_KEY, KEY);
        httpHeaders.set(HEADER_HOST, HOST);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        String gamesForDate = String.join("?", GAMES_URI, "dates[]=\"" + date + "\"");
        ResponseEntity<DataGames> response = restTemplate.exchange(gamesForDate, HttpMethod.GET, httpEntity, DataGames.class);
        return Objects.requireNonNull(response.getBody()).getGames();
    }

    @Override
    public Game getGame(Integer gameId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER_KEY, KEY);
        httpHeaders.set(HEADER_HOST, HOST);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        String singleGame = String.join("/", GAMES_URI, gameId.toString());

        ResponseEntity<Game> response = restTemplate.exchange(singleGame, HttpMethod.GET, httpEntity, Game.class);
        return response.getBody();
    }

    @Override
    public List<Player> getPlayersFromGame(long gameId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER_KEY, KEY);
        httpHeaders.set(HEADER_HOST, HOST);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        String singleGame = String.join("?", STATISTICS_URI, "game_ids[]=" + gameId);

        ResponseEntity<DataPlayers> response = restTemplate.exchange(singleGame, HttpMethod.GET, httpEntity, DataPlayers.class);
        return Objects.requireNonNull(response.getBody()).getPlayers();
    }
}
