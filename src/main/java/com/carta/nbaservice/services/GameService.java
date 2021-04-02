package com.carta.nbaservice.services;

import java.util.List;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.domain.Game;

public interface GameService {
    Game fetchGame(int gameId);
    List<Game> listGames(String date);
    Comment addCommentToGame(int gameId, String commentText);
    Comment modifyCommentOnGame(int commentId, String commentText);
}
