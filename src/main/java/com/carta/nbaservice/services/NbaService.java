package com.carta.nbaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.carta.nbaservice.entities.Data;
import com.carta.nbaservice.entities.Game;

@Service
public class NbaService {

    private final String FREE_NBA_URL = "https://free-nba.p.rapidapi.com/games";
    private final String KEY = "587e392567msh1060feed2027cdbp1cd6a7jsn1633bc5de72c";
    private final String HOST = "free-nba.p.rapidapi.com";
    private final String HEADER_KEY = "x-rapidapi-key";
    private final String HEADER_HOST = "x-rapidapi-host";

    private RestTemplate restTemplate;

    @Autowired
    public NbaService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public Data getAllGamesForDate(String date) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER_KEY, KEY);
        httpHeaders.set(HEADER_HOST, HOST);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        String gamesForDate = String.join("?", FREE_NBA_URL, "dates[]=\"" + date + "\"");
        ResponseEntity<Data> response = restTemplate.exchange(gamesForDate, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Data>() {});
        return response.getBody();
    }

    public Game getGame(Integer gameId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER_KEY, KEY);
        httpHeaders.set(HEADER_HOST, HOST);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        String singleGame = String.join("/", FREE_NBA_URL, gameId.toString());

        ResponseEntity<Game> response = restTemplate.exchange(singleGame, HttpMethod.GET, httpEntity, Game.class);
        return response.getBody();
    }
}
