package com.carta.nbaservice.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

    @JsonProperty("data")
    private List<Game> games;
    @JsonProperty("meta")
    private Metadata metadata;

    public Data(List<Game> games, Metadata metadata) {
        this.games = games;
        this.metadata = metadata;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
