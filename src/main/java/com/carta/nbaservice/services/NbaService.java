package com.carta.nbaservice.services;

import com.carta.nbaservice.entities.DataMatches;
import com.carta.nbaservice.entities.DataPlayers;
import com.carta.nbaservice.entities.Match;
import com.carta.nbaservice.entities.PlayerStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NbaService {

    private static final String GAMES_URI = "games";
    private static final String STATISTICS_URI = "stats";
    private static final int FIRST_PAGE = 1;

    private static final String SERVER_ERROR_MESSAGE = "An error has occurred on Free NBA API. Please contact RapidAPI for more information.";
    private static final String GAME_ID_NOT_FOUND_MESSAGE = "Game ID not found on Free NBA API";
    private static final String CLIENT_ERROR_MESSAGE = "An error has occurred upon requesting data to Free NBA API. Please contact your system administrator.";

    private final RestClient restClient;

    public List<Match> fetchAllGamesForDate(String date) {
        log.debug("Fetching games for date: {}", date);
        DataMatches response = fetchDataMatchesResponseEntity(date, FIRST_PAGE);

        List<Match> games = Objects.requireNonNull(response).getGames();
        int pageNumber = Objects.requireNonNull(response).getMetadata().getNextPage();
        boolean hasMorePlayers = pageNumber != 0;

        while (hasMorePlayers) {
            log.debug("Fetching games for page: {}", pageNumber);
            DataMatches nextPageResponse = fetchDataMatchesResponseEntity(date, pageNumber);
            games.addAll(Objects.requireNonNull(nextPageResponse).getGames());
            pageNumber = Objects.requireNonNull(nextPageResponse).getMetadata().getNextPage();
            if (pageNumber == 0) {
                hasMorePlayers = false;
            }
        }

        return games;
    }

    public List<PlayerStatistics> fetchPlayersFromGame(Integer gameId) {
        log.debug("Fetching players for game ID: {}", gameId);
        DataPlayers response = fetchDataPlayers(gameId, FIRST_PAGE);

        List<PlayerStatistics> playerStatistics = Objects.requireNonNull(response).getPlayers();
        int pageNumber = Objects.requireNonNull(response).getMetadata().getNextPage();
        boolean hasMorePlayers = pageNumber != 0;

        while (hasMorePlayers) {
            log.debug("Fetching players for page {}", pageNumber);
            DataPlayers nextPageResponse = fetchDataPlayers(gameId, pageNumber);
            playerStatistics.addAll(Objects.requireNonNull(nextPageResponse).getPlayers());
            pageNumber = Objects.requireNonNull(nextPageResponse).getMetadata().getNextPage();
            if (pageNumber == 0) {
                hasMorePlayers = false;
            }
        }

        return playerStatistics;
    }

    private DataPlayers fetchDataPlayers(Integer gameId, int firstPage) {
        String singleGameUrl = STATISTICS_URI + String.format("?game_ids[]=%d", gameId) + String.format("&page=%d", firstPage);
        return restClient.get()
            .uri(singleGameUrl)
            .retrieve()
            .onStatus(HttpStatusCode::is5xxServerError, (_, _) -> log.error(SERVER_ERROR_MESSAGE))
            .onStatus(HttpStatusCode::is4xxClientError, (_, response) -> {
                if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                    log.error(GAME_ID_NOT_FOUND_MESSAGE);
                } else {
                    log.error(CLIENT_ERROR_MESSAGE);
                }
            })
            .body(DataPlayers.class);
    }

    private DataMatches fetchDataMatchesResponseEntity(String date, int pageNumber) {
        String gamesForDate = GAMES_URI + String.format("?dates[]=\"%s\"", date) + String.format("&page=%d", pageNumber);
        return restClient.get()
            .uri(gamesForDate)
            .retrieve()
            .body(DataMatches.class);
    }
}