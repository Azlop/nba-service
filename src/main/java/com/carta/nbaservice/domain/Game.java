package com.carta.nbaservice.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "game")
public class Game {

    @Id
    private Integer gameId;
    @Column
    private LocalDate date;
    @Column
    private String homeTeamName;
    @Column
    private String awayTeamName;
    @Column
    private int homeTeamScore;
    @Column
    private int awayTeamScore;

    @OneToMany(cascade = { CascadeType.ALL })
    private List<Comment> comments;
    @OneToMany(cascade = { CascadeType.ALL })
    private List<Player> players;

    public Game(Integer gameId, LocalDate date, String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore, List<Comment> comments,
            List<Player> players) {
        this.gameId = gameId;
        this.date = date;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.comments = comments;
        this.players = players;
    }

    public Game() {}

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Game game = (Game) o;
        return homeTeamScore == game.homeTeamScore && awayTeamScore == game.awayTeamScore && Objects.equals(gameId, game.gameId) && Objects
                .equals(date, game.date) && Objects.equals(homeTeamName, game.homeTeamName) && Objects.equals(awayTeamName, game.awayTeamName)
                && Objects.equals(comments, game.comments) && Objects.equals(players, game.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, date, homeTeamName, awayTeamName, homeTeamScore, awayTeamScore, comments, players);
    }
}
