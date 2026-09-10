package com.carta.nbaservice.domain;

import com.carta.nbaservice.dtos.GamePointsDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "game")
@Data
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
}