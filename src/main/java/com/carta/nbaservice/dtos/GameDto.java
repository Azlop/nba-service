package com.carta.nbaservice.dtos;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "game")
public class GameDto {

    @Id
    private Integer gameId;
    @Column
    private String date;
    @Column
    private String homeTeamName;
    @Column
    private String awayTeamName;
    @Column
    private int homeTeamScore;
    @Column
    private int awayTeamScore;

    @OneToMany(cascade = { CascadeType.ALL })
    private List<CommentDto> comments;
    @OneToMany(cascade = { CascadeType.ALL })
    private List<PlayerDto> players;

    public GameDto(Integer gameId, String date, String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore, List<CommentDto> comments,
            List<PlayerDto> players) {
        this.gameId = gameId;
        this.date = date;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.comments = comments;
        this.players = players;
    }

    public GameDto() {}

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public List<PlayerDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDto> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GameDto gameDto = (GameDto) o;
        return homeTeamScore == gameDto.homeTeamScore && awayTeamScore == gameDto.awayTeamScore && Objects.equals(gameId, gameDto.gameId) && Objects
                .equals(date, gameDto.date) && Objects.equals(homeTeamName, gameDto.homeTeamName) && Objects.equals(awayTeamName, gameDto.awayTeamName)
                && Objects.equals(comments, gameDto.comments) && Objects.equals(players, gameDto.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, date, homeTeamName, awayTeamName, homeTeamScore, awayTeamScore, comments, players);
    }
}
