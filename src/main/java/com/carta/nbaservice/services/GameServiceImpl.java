package com.carta.nbaservice.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.domain.Game;
import com.carta.nbaservice.domain.Player;
import com.carta.nbaservice.repos.CommentRepository;
import com.carta.nbaservice.repos.GameRepository;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private final NbaService nbaService;

    private GameRepository gameRepository;
    private CommentRepository commentRepository;

    @Autowired
    public GameServiceImpl(NbaService nbaService) {
        this.nbaService = nbaService;
    }

    @Override
    public Game fetchGame(int gameId) {
        com.carta.nbaservice.entities.Game game = nbaService.getGame(gameId);
        List<Player> playersDtos = fetchPlayersPoints(gameId);
        Game gameDto = new Game();
        gameDto.setGameId(game.getId());
        gameDto.setDate(game.getDate());
        gameDto.setHomeTeamName(game.getHomeTeam().getName());
        gameDto.setAwayTeamName(game.getVisitorTeam().getName());
        gameDto.setHomeTeamScore(game.getHomeTeamScore());
        gameDto.setAwayTeamScore(game.getVisitorTeamScore());
        gameDto.setPlayers(playersDtos);
        return gameDto;
    }

    @Override
    public List<Game> listGames(String date) {
        List<com.carta.nbaservice.entities.Game> games = nbaService.getAllGamesForDate(date);
        List<Game> gameDtos = new ArrayList<>();

        for (com.carta.nbaservice.entities.Game game : games) {
            Game gameDto = new Game();
            gameDto.setGameId(game.getId());
            gameDto.setDate(game.getDate());
            gameDto.setHomeTeamName(game.getHomeTeam().getName());
            gameDto.setAwayTeamName(game.getVisitorTeam().getName());
            gameDto.setHomeTeamScore(game.getHomeTeamScore());
            gameDto.setAwayTeamScore(game.getVisitorTeamScore());
            gameDto.setPlayers(Collections.emptyList());
            gameDto.setComments(Collections.emptyList());
            gameDtos.add(gameDto);
        }

        return gameDtos;
    }

    @Override
    public Comment addCommentToGame(int gameId, String commentText) {
        verifyGameId(gameId);
        Comment comment = new Comment(gameId, commentText);
        return commentRepository.save(comment);
    }

    @Override
    public Comment modifyCommentOnGame(int commentId, String commentText) {
        Comment comment = verifyCommentId(commentId);
        comment.setText(commentText);
        return commentRepository.save(comment);
    }

    private Comment verifyCommentId(int commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new NoSuchElementException("Comment ID does not exist: " + commentId));
    }

    private void verifyGameId(int gameId) {
        gameRepository.findById(gameId).orElseThrow(() ->
                new NoSuchElementException("Game ID does not exist: " + gameId));
    }

    private List<Player> fetchPlayersPoints(int gameId) {
        List<com.carta.nbaservice.entities.Player> players = nbaService.getPlayersFromGame(gameId);
        return Collections.emptyList();
    }
}
