package com.carta.nbaservice.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataPlayers {

    @JsonProperty("data")
    private List<PlayerStatistics> players;
    @JsonProperty("meta")
    private Metadata metadata;
}