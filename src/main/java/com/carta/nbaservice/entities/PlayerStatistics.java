package com.carta.nbaservice.entities;

public class PlayerStatistics {

    private Player player;
    private int pts;

    public PlayerStatistics(Player player, int pts) {
        this.player = player;
        this.pts = pts;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }
}
