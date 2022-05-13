package com.carta.nbaservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@AllArgsConstructor
public class Match {

    private Integer id;
    private String date;
    @JsonProperty("home_team")
    private Team homeTeam;
    @JsonProperty("home_team_score")
    private int homeTeamScore;
    @JsonProperty("visitor_team")
    private Team visitorTeam;
    @JsonProperty("visitor_team_score")
    private int visitorTeamScore;
}