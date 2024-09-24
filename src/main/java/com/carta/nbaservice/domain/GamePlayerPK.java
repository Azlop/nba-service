package com.carta.nbaservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GamePlayerPK implements Serializable {

    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "player_id")
    private Integer playerId;
}