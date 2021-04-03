package com.carta.nbaservice.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataPlayers {

    @JsonProperty("data")
    private List<PlayerStatistics> players;
    @JsonProperty("meta")
    private Metadata metadata;

    public DataPlayers(List<PlayerStatistics> players, Metadata metadata) {
        this.players = players;
        this.metadata = metadata;
    }

    public List<PlayerStatistics> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerStatistics> players) {
        this.players = players;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
