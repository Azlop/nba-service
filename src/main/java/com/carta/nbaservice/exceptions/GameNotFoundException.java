package com.carta.nbaservice.exceptions;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(Integer gameId) {
        super("Game ID does not exist: " + gameId);
    }
}
