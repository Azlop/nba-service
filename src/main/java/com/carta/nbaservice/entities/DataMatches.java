package com.carta.nbaservice.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataMatches {

    @JsonProperty("data")
    private List<Match> games;
    @JsonProperty("meta")
    private Metadata metadata;

    public DataMatches(List<Match> games, Metadata metadata) {
        this.games = games;
        this.metadata = metadata;
    }

    public List<Match> getGames() {
        return games;
    }

    public void setGames(List<Match> games) {
        this.games = games;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
