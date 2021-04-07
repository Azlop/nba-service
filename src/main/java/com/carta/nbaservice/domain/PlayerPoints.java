package com.carta.nbaservice.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class PlayerPoints implements Serializable {

    @EmbeddedId private GamePlayerPK gamePlayerPK;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "player_id")
    private Player player;

    @Column
    private int points;

    public PlayerPoints(GamePlayerPK gamePlayerPK, Game game, Player player, int points) {
        this.gamePlayerPK = gamePlayerPK;
        this.game = game;
        this.player = player;
        this.points = points;
    }

    public PlayerPoints() {}

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PlayerPoints that = (PlayerPoints) o;
        return points == that.points && Objects.equals(gamePlayerPK, that.gamePlayerPK) && Objects.equals(game, that.game) && Objects
                .equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gamePlayerPK, game, player, points);
    }
}
