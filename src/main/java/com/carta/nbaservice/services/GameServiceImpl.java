package com.carta.nbaservice.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.domain.Player;
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
        LOGGER.debug("Successfully got game");
        return game.get();
    }

    @Override
    public List<Game> listGames(LocalDate date) {
        LOGGER.debug("Getting games for date: {}", date);
        List<Game> games = this.gameRepository.findByDate(date);

        if (games.isEmpty()) {
            List<com.carta.nbaservice.entities.Game> matches = nbaService.getAllGamesForDate(date.toString());

            for (com.carta.nbaservice.entities.Game match : matches) {
                List<Player> players = getPlayersPoints(match.getId());
                List<Comment> comments = fetchComments(match.getId());
                Game game = createGame(match, players, comments);
                this.gameRepository.save(game);
                games.add(game);
            }
        }

        LOGGER.debug("Successfully got games");
        return games;
    }

    private Optional<Game> saveGame(int gameId) {
        Optional<Game> game;
        com.carta.nbaservice.entities.Game match = nbaService.getGame(gameId);
        List<Player> players = getPlayersPoints(gameId);
        List<Comment> comments = fetchComments(gameId);
        game = Optional.of(createGame(match, players, comments));
        this.gameRepository.save(game.get());
        return game;
    }

    private List<Player> getPlayersPoints(int gameId) {
        LOGGER.debug("Getting players for game ID: {}", gameId);
        List<PlayerStatistics> playerStatistics = nbaService.getPlayersFromGame(gameId);
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
        return commentRepository.findByGameIdOrderByTimestampDesc(gameId);
    }

    private Game createGame(com.carta.nbaservice.entities.Game game, List<Player> players, List<Comment> comments) {
        Game gameDto = new Game();
        gameDto.setGameId(game.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gameDto.setDate(LocalDate.parse(game.getDate(), formatter));
        gameDto.setHomeTeamName(game.getHomeTeam().getName());
        gameDto.setAwayTeamName(game.getVisitorTeam().getName());
        gameDto.setHomeTeamScore(game.getHomeTeamScore());
        gameDto.setAwayTeamScore(game.getVisitorTeamScore());
        gameDto.setPlayers(players);
        gameDto.setComments(comments);
        return gameDto;
    }
}
