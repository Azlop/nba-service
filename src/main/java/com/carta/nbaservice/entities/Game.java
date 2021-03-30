package com.carta.nbaservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Game {

    private Integer id;
    private String date;
    @JsonProperty("home_team")
    private Team homeTeam;
    @JsonProperty("home_team_score")
    private int homeTeamScore;
    private int period;
    private boolean postseason;
    private int season;
    private String status;
    private String time;
    @JsonProperty("visitor_team")
    private Team visitorTeam;
    @JsonProperty("visitor_team_score")
    private int visitorTeamScore;


    public Game(Integer id, String date, Team homeTeam, int homeTeamScore, int period, boolean postseason, int season, String status, String time,
            Team visitorTeam, int visitorTeamScore) {
        this.id = id;
        this.date = date;
        this.homeTeam = homeTeam;
        this.homeTeamScore = homeTeamScore;
        this.period = period;
        this.postseason = postseason;
        this.season = season;
        this.status = status;
        this.time = time;
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

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public boolean isPostseason() {
        return postseason;
    }

    public void setPostseason(boolean postseason) {
        this.postseason = postseason;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
