package com.carta.nbaservice.services;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.domain.GamePlayerPK;
import com.carta.nbaservice.domain.Player;
import com.carta.nbaservice.domain.PlayerPoints;
import com.carta.nbaservice.dtos.GamePointsDto;
import com.carta.nbaservice.entities.Match;
import com.carta.nbaservice.entities.PlayerStatistics;
import com.carta.nbaservice.exceptions.GameNotFoundException;
import com.carta.nbaservice.repos.CommentRepository;
import com.carta.nbaservice.repos.GameRepository;
import com.carta.nbaservice.repos.PlayerPointsRepository;
import com.carta.nbaservice.repos.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    public static final Logger LOGGER = LoggerFactory.getLogger(GameServiceImpl.class);

    private final NbaService nbaService;
    private final GameRepository gameRepository;
    private final CommentRepository commentRepository;
    private final PlayerRepository playerRepository;
    private final PlayerPointsRepository playerPointsRepository;

    @Override
    public Game getGame(int gameId) {
        LOGGER.debug("Getting game for ID: {}", gameId);

        Game game = verifyGameId(gameId);
        game.setComments(getComments(gameId));
        game.setGamePoints(getPlayersPointsByGameId(gameId));

        LOGGER.debug("Successfully got game information");
        return game;
    }

    @Override
    public List<Game> listGames(LocalDate date) {
        LOGGER.debug("Getting games for date: {}", date);
        List<Game> games = this.gameRepository.findByDate(date);

        if (games.isEmpty()) {
            List<Match> matches = this.nbaService.fetchAllGamesForDate(date.toString());

            for (Match match : matches) {
                Game game = transformToEntity(match);
                this.gameRepository.save(game);
                getAndSavePlayersPoints(game);
                game.setComments(getComments(game.getGameId()));
                game.setGamePoints(getPlayersPointsByGameId(game.getGameId()));
                games.add(game);
            }
        }

        for (Game game : games) {
            game.setComments(getComments(game.getGameId()));
            game.setGamePoints(getPlayersPointsByGameId(game.getGameId()));
        }

        LOGGER.debug("Successfully got games");
        return games;
    }

    private void getAndSavePlayersPoints(Game game) {
        LOGGER.debug("Getting players for game ID: {}", game.getGameId());
        List<PlayerStatistics> playerStatistics = this.nbaService.fetchPlayersFromGame(game.getGameId());

        List<Player> players = new ArrayList<>();
        List<PlayerPoints> playerPoints = new ArrayList<>();

        LOGGER.debug("Starting to save players in database");
        for (PlayerStatistics playerStatistic : playerStatistics) {
            if (playerStatistic.getPts() > 0) {
                Player player = new Player();
                player.setFirstName(playerStatistic.getPlayer().getFirstName());
                player.setLastName(playerStatistic.getPlayer().getLastName());

                players.add(player);
                playerPoints.add(new PlayerPoints(new GamePlayerPK(game.getId(), player.getId()), game, player, playerStatistic.getPts()));
            }
        }

        this.playerRepository.saveAll(players);
        this.playerPointsRepository.saveAll(playerPoints);
        LOGGER.debug("Successfully saved players in database");
    }

    private List<Comment> getComments(int gameId) {
        LOGGER.debug("Getting comments for game ID: {}", gameId);
        return this.commentRepository.findByGameIdOrderByTimestampDesc(gameId);
    }

    private Game transformToEntity(Match match) {
        Game game = new Game();
        game.setGameId(match.getId());
        game.setDate(parseDate(match.getDate()));
        game.setHomeTeamName(match.getHomeTeam().getName());
        game.setAwayTeamName(match.getVisitorTeam().getName());
        game.setHomeTeamScore(match.getHomeTeamScore());
        game.setAwayTeamScore(match.getVisitorTeamScore());
        return game;
    }

    private LocalDate parseDate(String date) {
        List<String> datePatterns = Arrays.asList("yyyy-MM-dd HH:mm:ss 'UTC'", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        for (String datePattern : datePatterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException exception) {
                LOGGER.warn("Could not parse date \"{}\" with pattern \"{}\"",
                        date, datePattern);
            }
        }
        LOGGER.error("Date could not be parsed with the available patterns");
        return LocalDate.now();
    }

    private List<GamePointsDto> getPlayersPointsByGameId(int gameId) {
        return gameRepository.getPlayersPointsByGameId(gameId);
    }

    private Game verifyGameId(int gameId) {
        LOGGER.debug("Verifying existence of game for ID: {}", gameId);
        return gameRepository.findByGameId(gameId).orElseThrow(() -> {
            LOGGER.error("Game ID not found: {}", gameId);
            return new GameNotFoundException(gameId);
        });
    }
}