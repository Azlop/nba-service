package com.carta.nbaservice.services;

import com.carta.nbaservice.configurations.AppConfig;
import com.carta.nbaservice.entities.DataMatches;
import com.carta.nbaservice.entities.DataPlayers;
import com.carta.nbaservice.entities.Match;
import com.carta.nbaservice.entities.PlayerStatistics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(MockitoExtension.class)
class NbaServiceImplTest {

    private NbaService nbaService;
    @Mock
    private RestTemplate restTemplate;
    private HttpEntity<String> httpEntity;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void init() {
        AppConfig appConfig = new AppConfig();
        appConfig.setHost("localhost");
        appConfig.setBaseUrl("https://localhost");
        appConfig.setKey("key");
        appConfig.setHeaders(Map.of("headerKey", "headerValue"));
        nbaService = new NbaServiceImpl(restTemplate, appConfig);
        HttpHeaders httpHeaders = new HttpHeaders();
        appConfig.getHeaders().forEach(httpHeaders::set);
        this.httpEntity = new HttpEntity<>(httpHeaders);
    }

    @Test
    void givenNonExistingGameId_whenGettingGame_thenShouldReturnNotFound() {
//        int gameId = Integer.MAX_VALUE;
//
//        Exception exception = assertThrows(HttpClientErrorException.class, () -> );
//
//        String expectedMessage = "Game ID not found";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenDate_whenGettingGames_thenShouldReturnAllGamesForTheDate() throws JsonProcessingException {
        String date = "2021-03-28";
        String json = readJsonFile("/games_by_date.json");
        DataMatches dataMatches = objectMapper.readValue(json, DataMatches.class);
        ResponseEntity<DataMatches> responseEntityDataMatches = new ResponseEntity<>(dataMatches, HttpStatus.OK);
        Mockito.when(restTemplate.exchange("games?dates[]=\"2021-03-28\"&page=1", HttpMethod.GET,
                httpEntity, DataMatches.class))
            .thenReturn(responseEntityDataMatches);

        List<Match> matches = nbaService.fetchAllGamesForDate(date);

        assertThat(matches, hasSize(4));
    }

    @Test
    void givenGameIdWithTwoPages_whenGettingPlayerStatistics_thenShouldReturnPlayersStatisticsForGame() throws
        JsonProcessingException {
        Integer gameId = 264405;
        String jsonPage1 = readJsonFile("/stats_for_game_page_1.json");
        String jsonPage2 = readJsonFile("/stats_for_game_page_2.json");
        DataPlayers dataPlayersPage1 = objectMapper.readValue(jsonPage1, DataPlayers.class);
        DataPlayers dataPlayersPage2 = objectMapper.readValue(jsonPage2, DataPlayers.class);
        ResponseEntity<DataPlayers> responseEntityDataMatchesPage1 = new ResponseEntity<>(dataPlayersPage1, HttpStatus.OK);
        ResponseEntity<DataPlayers> responseEntityDataMatchesPage2 = new ResponseEntity<>(dataPlayersPage2,
            HttpStatus.OK);
        Mockito.when(restTemplate.exchange("stats?game_ids[]=264405&page=1", HttpMethod.GET,
                httpEntity, DataPlayers.class))
            .thenReturn(responseEntityDataMatchesPage1);
        Mockito.when(restTemplate.exchange("stats?game_ids[]=264405&page=2", HttpMethod.GET,
                httpEntity, DataPlayers.class))
            .thenReturn(responseEntityDataMatchesPage2);

        List<PlayerStatistics> players = nbaService.fetchPlayersFromGame(gameId);

        assertThat(players, hasSize(27));
    }

    @SneakyThrows
    private String readJsonFile(String jsonFilePath) {
        return Files.readString(Path.of(Objects.requireNonNull(getClass().getResource(jsonFilePath)).toURI()));
    }
}