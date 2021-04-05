package com.carta.nbaservice.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.domain.Player;
import com.carta.nbaservice.entities.Match;
import com.carta.nbaservice.entities.PlayerStatistics;
import com.carta.nbaservice.repos.CommentRepository;
import com.carta.nbaservice.repos.GameRepository;

@Service
public class GameServiceImpl implements GameService {

    public static final Logger LOGGER = LoggerFactory.getLogger(GameServiceImpl.class);

    @Autowired
    private final NbaService nbaService;

    @Autowired
    private final GameRepository gameRepository;

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    public GameServiceImpl(NbaService nbaService, GameRepository gameRepository, CommentRepository commentRepository) {
        this.nbaService = nbaService;
        this.gameRepository = gameRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Game getGame(int gameId) {
        LOGGER.debug("Getting game for ID: {}", gameId);
        Optional<Game> game = this.gameRepository.findById(gameId);

        if (!game.isPresent()) {
            game = saveGame(gameId);
        }

        game.get().setComments(fetchComments(gameId));

        LOGGER.debug("Successfully got game");
        return game.get();
    }

    @Override
    public List<Game> listGames(LocalDate date) {
        LOGGER.debug("Getting games for date: {}", date);
        List<Game> games = this.gameRepository.findByDate(date);

        if (games.isEmpty()) {
            List<Match> matches = this.nbaService.getAllGamesForDate(date.toString());

            for (Match match : matches) {
                List<Player> players = getPlayersPoints(match.getId());
                Game game = createGame(match, players);
                this.gameRepository.save(game);
                games.add(game);
            }
        }

        LOGGER.debug("Successfully got games");
        return games;
    }

    private Optional<Game> saveGame(int gameId) {
        Optional<Game> game;
        Match match = this.nbaService.getGame(gameId);
        List<Player> players = getPlayersPoints(gameId);
        game = Optional.of(createGame(match, players));
        this.gameRepository.save(game.get());
        return game;
    }

    private List<Player> getPlayersPoints(int gameId) {
        LOGGER.debug("Getting players for game ID: {}", gameId);
        List<PlayerStatistics> playerStatistics = this.nbaService.getPlayersFromGame(gameId);
        List<Player> players = new ArrayList<>();

        for (PlayerStatistics playerStatistic : playerStatistics) {
            if (playerStatistic.getPts() > 0) {
                Player player = new Player();
                player.setFirstName(playerStatistic.getPlayer().getFirstName());
                player.setLastName(playerStatistic.getPlayer().getLastName());
                player.setPoints(playerStatistic.getPts());
                players.add(player);
            }
        }

        LOGGER.debug("Successfully got players");
        return players;
    }

    private List<Comment> fetchComments(int gameId) {
        LOGGER.debug("Getting comments for game ID: {}", gameId);
        return this.commentRepository.findByGameIdOrderByTimestampDesc(gameId);
    }

    private Game createGame(Match match, List<Player> players) {
        Game game = new Game();
        game.setGameId(match.getId());
        game.setDate(parseDate(match.getDate()));
        game.setHomeTeamName(match.getHomeTeam().getName());
        game.setAwayTeamName(match.getVisitorTeam().getName());
        game.setHomeTeamScore(match.getHomeTeamScore());
        game.setAwayTeamScore(match.getVisitorTeamScore());
        game.setPlayers(players);
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
        return null;
    }
}
