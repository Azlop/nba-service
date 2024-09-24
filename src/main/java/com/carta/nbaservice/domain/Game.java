package com.carta.nbaservice.domain;

import com.carta.nbaservice.dtos.GamePointsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "game")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game implements Serializable {

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