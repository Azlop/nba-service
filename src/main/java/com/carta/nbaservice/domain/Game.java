package com.carta.nbaservice.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.carta.nbaservice.dtos.GamePointsDto;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue
    private Integer id;
    @Column
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

    @OneToMany(mappedBy = "game")
    private List<PlayerPoints> points;

    @Transient
    public List<GamePointsDto> gamePoints;

    public Game(Integer gameId, LocalDate date, String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore) {
        this.gameId = gameId;
        this.date = date;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<PlayerPoints> getPoints() {
        return points;
    }

    public void setPoints(List<PlayerPoints> points) {
        this.points = points;
    }

    public List<GamePointsDto> getGamePoints() {
        return gamePoints;
    }

    public void setGamePoints(List<GamePointsDto> gamePoints) {
        this.gamePoints = gamePoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Game game = (Game) o;
        return homeTeamScore == game.homeTeamScore && awayTeamScore == game.awayTeamScore && Objects.equals(gameId, game.gameId) && Objects
                .equals(date, game.date) && Objects.equals(homeTeamName, game.homeTeamName) && Objects.equals(awayTeamName, game.awayTeamName) && Objects
                .equals(comments, game.comments) && Objects.equals(points, game.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, date, homeTeamName, awayTeamName, homeTeamScore, awayTeamScore, comments, points);
    }
}
