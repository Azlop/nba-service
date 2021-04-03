package com.carta.nbaservice.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

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
        com.carta.nbaservice.entities.Game game = nbaService.getGame(gameId);
        List<Player> players = getPlayersPoints(gameId);
        List<Comment> comments = fetchComments(gameId);
        Game gameDto = createGame(game, players, comments);
        LOGGER.debug("Successfully got game");
        return gameDto;
    }

    @Override
    public List<Game> listGames(String date) {
        LOGGER.debug("Getting games for date: {}", date);
        List<com.carta.nbaservice.entities.Game> games = nbaService.getAllGamesForDate(date);
        List<Game> gameDtos = new ArrayList<>();

        for (com.carta.nbaservice.entities.Game game : games) {
            Game gameDto = createGame(game, Collections.emptyList(), Collections.emptyList());
            gameDtos.add(gameDto);
        }

        LOGGER.debug("Successfully got games");
        return gameDtos;
    }

    private void verifyGameId(int gameId) {
        com.carta.nbaservice.entities.Game game = nbaService.getGame(gameId);
        if (game == null) {
            throw new NoSuchElementException("Game ID does not exist: " + gameId);
        }
//        gameRepository.findById(gameId).orElseThrow(() ->
//                new NoSuchElementException("Game ID does not exist: " + gameId);
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

        return players;
    }

    private List<Comment> fetchComments(int gameId) {
        LOGGER.debug("Getting comments for game ID: {}", gameId);
        return commentRepository.findByGameIdOrderByTimestampDesc(gameId);
    }

    private Game createGame(com.carta.nbaservice.entities.Game game, List<Player> players, List<Comment> comments) {
        Game gameDto = new Game();
        gameDto.setGameId(game.getId());
        gameDto.setDate(game.getDate());
        gameDto.setHomeTeamName(game.getHomeTeam().getName());
        gameDto.setAwayTeamName(game.getVisitorTeam().getName());
        gameDto.setHomeTeamScore(game.getHomeTeamScore());
        gameDto.setAwayTeamScore(game.getVisitorTeamScore());
        gameDto.setPlayers(players);
        gameDto.setComments(comments);
        return gameDto;
    }
}
