package com.carta.nbaservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

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


    public Match(Integer id, String date, Team homeTeam, int homeTeamScore, Team visitorTeam, int visitorTeamScore) {
        this.id = id;
        this.date = date;
        this.homeTeam = homeTeam;
        this.homeTeamScore = homeTeamScore;
        this.visitorTeam = visitorTeam;
        this.visitorTeamScore = visitorTeamScore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public Team getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(Team visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public int getVisitorTeamScore() {
        return visitorTeamScore;
    }

    public void setVisitorTeamScore(int visitorTeamScore) {
        this.visitorTeamScore = visitorTeamScore;
    }
}
