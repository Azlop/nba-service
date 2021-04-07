package com.carta.nbaservice.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GamePlayerPK implements Serializable {

    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "player_id")
    private Integer playerId;

    public GamePlayerPK(Integer gameId, Integer playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public GamePlayerPK() {}

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GamePlayerPK that = (GamePlayerPK) o;
        return Objects.equals(gameId, that.gameId) && Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, playerId);
    }
}
