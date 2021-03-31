package com.carta.nbaservice.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataPlayers {

    @JsonProperty("data")
    private List<Player> players;
    @JsonProperty("meta")
    private Metadata metadata;

    public DataPlayers(List<Player> players, Metadata metadata) {
        this.players = players;
        this.metadata = metadata;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
